package com.lrsoft.xnovelreader.HTMLAnalysis;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;

import com.lrsoft.xnovelreader.TransmissionMiddleware.BookItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class BookSearch extends Thread{
    private final String biqugeSearch = "https://so.biqusoso.com/s.php?ie=utf-8&siteid=biqiuge.com&q=%s";
    private final String biqugeMethod = "div[id=wrapper] > div[id=search-main] > div[class=search-list] > ul > li";
    private ArrayAdapter<BookItem> updateAdapter;
    private String bookName;
    public BookSearch(ArrayAdapter<BookItem> adapter, String searchName){
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
                BookItem temp = new BookItem();
                String getName = elementArticle.get(i).text();
                String getURL  = elementArticle.get(i).attr("href").toString();
                String author = elementAuthor.get(i).text();
                temp.setBookAuthor(author);
                temp.setBookName(getName);
                temp.setBookChapterURL(getURL);
                Message msg = mHandler.obtainMessage(1, temp);
                msg.sendToTarget();
            }
            if(elementArticle.size()==0){
                BookItem errorContainer = new BookItem();
                errorContainer.setBookAuthor("N/A");
                errorContainer.setBookName("没有搜索到结果！");
                errorContainer.setBookChapterURL("");
                Message msg = mHandler.obtainMessage(1, errorContainer);
                msg.sendToTarget();
            }
        }catch (IOException exp){
            BookItem errorContainer = new BookItem();
            errorContainer.setBookAuthor(exp.getLocalizedMessage());
            errorContainer.setBookName("加载时发生错误！");
            errorContainer.setBookChapterURL("");
        }
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    List<BookItem> list = (List<BookItem>)msg.obj;
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
                    BookItem item = (BookItem)msg.obj;
                    updateAdapter.add(item);
                    updateAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
