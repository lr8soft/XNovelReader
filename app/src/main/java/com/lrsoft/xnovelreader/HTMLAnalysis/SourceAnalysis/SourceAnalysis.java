package com.lrsoft.xnovelreader.HTMLAnalysis.SourceAnalysis;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.lrsoft.xnovelreader.TransmissionMiddleware.BookItem;
import com.lrsoft.xnovelreader.TransmissionMiddleware.ChapterListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
public class SourceAnalysis {
    private BiqugeWebsite source_biquge = new BiqugeWebsite();
    private DingdiannWebsite source_dingdiann = new DingdiannWebsite();
    private Vector<IWebSource> NovelSource = new Vector<>();
    public enum WebSiteSource{
        Biquge, Dingdiann
    };
    public SourceAnalysis(){
        NovelSource.add(source_dingdiann);
        NovelSource.add(source_biquge);
    }
    public List<BookItem> getBookFromAllSource(String bookName){
        List<BookItem> retBook = new ArrayList<>();
        for(int i=0; i<NovelSource.size(); i++){
            retBook.addAll(NovelSource.get(i).searchBookFromWebsites(bookName));
        }
        return  retBook;
    }
    public void getBookFromAllSourceOneByOne(String bookName, Handler mHandler){
        boolean isEmpty = true;
        Message preMsg = mHandler.obtainMessage(-1);
        preMsg.sendToTarget();
        for(int i=0; i<NovelSource.size(); i++){
            isEmpty = NovelSource.get(i).searchBookFromWebsiteOneByOne(bookName, mHandler);
            if(isEmpty){
                BookItem errorContainer = new BookItem();
                errorContainer.setBookAuthor("N/A");
                errorContainer.setBookName(NovelSource.get(i).getSourceName()+"没有搜索到结果！");
                errorContainer.setBookChapterURL("");
                Message msg = mHandler.obtainMessage(1, errorContainer);
                msg.sendToTarget();
            }
            isEmpty = false;
        }

    }
    public List<ChapterListItem> getChapterListFromSource(String bookURL,WebSiteSource type) throws Exception{
        List<ChapterListItem> list = null;
        switch (type){
            case Biquge:
                list = source_biquge.getChapterFromWebsites(bookURL);
                break;
            case Dingdiann:
                list = source_dingdiann.getChapterFromWebsites(bookURL);
                break;
        }
        return list;
    }
    public String getArticleFromSource(String chapterURL,WebSiteSource type) throws Exception{
        String returnStr = null;
        switch (type){
            case Biquge:
                returnStr = source_biquge.getArticleFromWebsites(chapterURL);
                break;
            case Dingdiann:
                returnStr = source_dingdiann.getArticleFromWebsites(chapterURL);
                break;
        }
        return returnStr;
    }
}
