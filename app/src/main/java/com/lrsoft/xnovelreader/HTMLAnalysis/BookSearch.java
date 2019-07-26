package com.lrsoft.xnovelreader.HTMLAnalysis;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lrsoft.xnovelreader.R;
import com.lrsoft.xnovelreader.SearchItem.SearchItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookSearch extends Thread{
    private final String biqugeSearch = "https://so.biqusoso.com/s.php?ie=utf-8&siteid=biqiuge.com&q=%s";
    private final String biqugeMethod = "div[id=wrapper] > div[id=search-main] > div[class=search-list] > ul > li";
    private ArrayAdapter<SearchItem> updateAdapter;
    private String bookName;
    public BookSearch(ArrayAdapter<SearchItem> adapter,String searchName){
        updateAdapter = adapter;
        bookName = searchName;
    }
    @Override
    public void run() {
        Looper.prepare();
        updateSearchResult(bookName);
    }
    public void updateSearchResult(String searchName){
        try{
            String url = String.format(biqugeSearch,searchName);
            Document doc = Jsoup.connect(url)
                    .get();
            Elements elementArticle = doc.select("span[class=s2] > a");
            Elements elementAuthor = doc.select("span[class=s4]");
            Message preMsg = mHandler.obtainMessage(-1);
            preMsg.sendToTarget();
            for(int i=0; i<elementArticle.size(); i++){
                SearchItem temp = new SearchItem();
                String getName = elementArticle.get(i).text();
                String getURL  = elementArticle.get(i).attr("href").toString();
                String author = elementAuthor.get(i).text();
                temp.bookAuthor = author;
                temp.bookName = getName;
                temp.bookChapterURL = getURL;
                temp.HTMLAnalysisType = SearchItem.AnalysisType.biqiuge;

                Message msg = mHandler.obtainMessage(1, temp);
                msg.sendToTarget();
            }
            if(elementArticle.size()==0){
                SearchItem errorContainer = new SearchItem();
                errorContainer.bookName="没有搜索到结果！";
                errorContainer.bookAuthor = "N/A";
                errorContainer.bookChapterURL="";
                Message msg = mHandler.obtainMessage(1, errorContainer);
                msg.sendToTarget();
            }
        }catch (IOException exp){
            SearchItem errorContainer = new SearchItem();
            errorContainer.bookName="加载时发生错误！";
            errorContainer.bookAuthor = exp.getLocalizedMessage();
            errorContainer.bookChapterURL="";
        }
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    List<SearchItem> list = (List<SearchItem>)msg.obj;
                    updateAdapter.clear();
                    for(int i=0; i<list.size(); i++){
                        updateAdapter.add(list.get(i));
                    }
                    updateAdapter.notifyDataSetChanged();
                    break;
                case -1:
                    updateAdapter.clear();
                    break;
                case 1:
                    SearchItem item = (SearchItem)msg.obj;
                    updateAdapter.add(item);
                    updateAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
