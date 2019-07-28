package com.lrsoft.xnovelreader.HTMLAnalysis.SourceAnalysis;

import android.os.Handler;

import com.lrsoft.xnovelreader.TransmissionMiddleware.BookItem;
import com.lrsoft.xnovelreader.TransmissionMiddleware.ChapterListItem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;

public interface IWebSource {
    List<BookItem> searchBookFromWebsites(String bookName);
    //msg.what:
    //0 should update all in one time
    //1 update book one by one
    //-1 clear book list
    boolean searchBookFromWebsiteOneByOne(String bookName, Handler mhandler);
    List<ChapterListItem> getChapterFromWebsites(String bookURL)  throws Exception;
    String getArticleFromWebsites(String chapterURL)  throws Exception;
    String getSourceName();
}
