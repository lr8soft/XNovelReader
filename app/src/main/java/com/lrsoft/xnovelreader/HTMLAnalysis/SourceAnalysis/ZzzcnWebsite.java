package com.lrsoft.xnovelreader.HTMLAnalysis.SourceAnalysis;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lrsoft.xnovelreader.HTMLAnalysis.HttpsHelper;
import com.lrsoft.xnovelreader.TransmissionMiddleware.BookItem;
import com.lrsoft.xnovelreader.TransmissionMiddleware.ChapterListItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZzzcnWebsite implements IWebSource {
        private final String ZzzcnSearch = "http://www.530p.com/s/%s";
        private final String ZzzcnWebsiteNow = "http://www.530p.com";
        @Override
        public List<BookItem> searchBookFromWebsites(String bookName) {
            List<BookItem> list = new ArrayList<>();
            try{
                String url = String.format(ZzzcnSearch,bookName);
                HttpsHelper.HttpsPrepare();
                Document doc = Jsoup.connect(url)
                        .get();
                /*div[id=wrapper] > div[id=main] > div[class=novelslist2] > ul > li > */
                Elements elementArticle = doc.select("li[class=conter1] > a");
                Elements elementAuthor = doc.select( "li[class=conter4]");
                for(int i=0; i<elementArticle.size(); i++){
                    BookItem temp = new BookItem();
                    String getName = elementArticle.get(i).text();
                    String getURL  = elementArticle.get(i).attr("href").toString();
                    String author = elementAuthor.get(i).text();
                    temp.setBookAuthor(author);
                    temp.setBookName(getName);
                    temp.setBookChapterURL(getURL);
                    list.add(temp);
                }
            }catch (IOException exp){
                BookItem errorContainer = new BookItem();
                errorContainer.setBookAuthor(exp.getLocalizedMessage());
                errorContainer.setBookName("源:"+getSourceName()+ "读取时发生错误！");
                errorContainer.setBookChapterURL("");
                list.add(errorContainer);
            }
            return list;
        }

        @Override
        public boolean searchBookFromWebsiteOneByOne(String bookName, Handler mhandler) {
            boolean isEmpty = true;
            try{
                String url = String.format(ZzzcnSearch,bookName);
                HttpsHelper.HttpsPrepare();
                Document doc = Jsoup.connect(url)
                        .get();
                Elements elementArticle = doc.select("li[class=conter1] > a");
                Elements elementAuthor = doc.select( "li[class=conter4]");
                for(int i=0; i<elementArticle.size(); i++){
                    BookItem temp = new BookItem();
                    String getName = elementArticle.get(i).text();
                    String getURL  = ZzzcnWebsiteNow + elementArticle.get(i).attr("href").toString();
                    String author = elementAuthor.get(i).text();
                    temp.setBookAuthor(author);
                    temp.setBookName(getName);
                    temp.setBookChapterURL(getURL);
                    Message msg = mhandler.obtainMessage(1, temp);
                    msg.sendToTarget();
                    isEmpty=false;
                }
            }catch (IOException exp){
                BookItem errorContainer = new BookItem();
                errorContainer.setBookAuthor(exp.getLocalizedMessage());
                errorContainer.setBookName("源:"+getSourceName()+" 读取时发生错误！");
                errorContainer.setBookChapterURL("");
                Message msg = mhandler.obtainMessage(1, errorContainer);
                msg.sendToTarget();
            }
            return isEmpty;
        }

        @Override
        public List<ChapterListItem> getChapterFromWebsites(String bookURL) throws Exception{
            List<ChapterListItem> list = new ArrayList<>();
            HttpsHelper.HttpsPrepare();
            Document doc = Jsoup.connect(bookURL).get();
            Elements elements = doc.select("div[class=conter] > div[class=clc]");
            int i=0;
            for(Element e:elements){
                String chapterURL = e.select("a").attr("href").toString();
                String chapterName = e.select("a").text();
                ChapterListItem temp = new ChapterListItem();
                temp.chapterID = i;
                temp.chapterName = chapterName;
                temp.chapterURL = ZzzcnWebsiteNow + chapterURL;
                list.add(temp);
                i++;
            }
            return list;
        }
        @Override
        public String getArticleFromWebsites(String chapterURL) throws Exception{
            String getArticleInfo = null;

            try{
                getArticleInfo = loadFromURL(chapterURL);
            }catch (Exception exp){
                try{
                    getArticleInfo = loadFromURL(chapterURL);
                }catch (Exception exp2){
                    throw exp2;
                }
            }
            return getArticleInfo;
        }
        private String loadFromURL(String chapterURL) throws Exception{
            HttpsHelper.HttpsPrepare();
            Document doc = Jsoup.connect(chapterURL).get();
            Elements articleInfo = doc.select("div[id=cp_content]");
            String chapterInfo = "";
            for(Element e:articleInfo){
                String temp = e
                        .html()
                        .replace("&nbsp;"," ")
                        .replace("<br>","\n")
                        .replace("<script language=\"javascript\">setFontSize();</script>","");
                if(!temp.equals("")){
                    chapterInfo+= temp;
                }
            }
            return chapterInfo;
        }

        @Override
        public String getSourceName() {
            return "3z中文网";
        }

    }

