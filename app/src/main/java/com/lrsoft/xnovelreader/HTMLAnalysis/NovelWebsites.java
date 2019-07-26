package com.lrsoft.xnovelreader.HTMLAnalysis;

import android.graphics.Bitmap;
import android.util.Log;

import com.lrsoft.xnovelreader.SearchItem.SearchItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class NovelWebsites {
    private final String biqugeSearch = "https://so.biqusoso.com/s.php?ie=utf-8&siteid=biqiuge.com&q=%s";
    private final String biqugeMethod = "div[id=wrapper] > div[id=search-main] > div[class=search-list] > ul > li";
    private Map<String,String> NovelWebsitesList;
    private String searchName;
    private NovelWebsites(){
        NovelWebsitesList = new HashMap<>();
        NovelWebsitesList.put(biqugeSearch,biqugeMethod);
    }
    /*!It should be called by child tread!*/
    public NovelWebsites(String searchName){
        this();
        this.searchName = searchName;
    }
    public List<SearchItem> getSearchResult(){
        List<SearchItem> list = new ArrayList<>();
        try{
            String url = String.format(biqugeSearch,searchName);
            Document doc = Jsoup.connect(url)
                    .get();
           // Log.i("getSearchResult: ", doc.html());
            Elements elementArticle = doc.select("span[class=s2] > a");
            Elements elementAuthor = doc.select("span[class=s4]");
            for(int i=0; i<elementArticle.size(); i++){
                SearchItem temp = new SearchItem();
                String getName = elementArticle.get(i).text();
                String getURL  = elementArticle.get(i).attr("href").toString();
                String author = elementAuthor.get(i).text();
                temp.bookAuthor = author;
                temp.bookName = getName;
                temp.bookChapterURL = getURL;
                temp.HTMLAnalysisType = SearchItem.AnalysisType.biqiuge;
                list.add(temp);
            }
        }catch (IOException exp){
            Log.i("getSearchResult: ",exp.getLocalizedMessage());
        }
        return list;
    }
}
