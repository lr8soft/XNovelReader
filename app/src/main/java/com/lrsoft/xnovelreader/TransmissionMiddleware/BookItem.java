package com.lrsoft.xnovelreader.TransmissionMiddleware;

import android.graphics.Bitmap;

public class BookItem {
    private Bitmap bitmap;
    private String bookName = "", bookAuthor = "";
    private String bookChapterURL = "";
    private boolean bookDownload = false;
    private String bookLocalizationName = "";
    public BookItem(){
        bitmap = null;
    }
    public BookItem(String name,String author){
        bookName = name;
        bookAuthor = author;
        bitmap = null;
    }
    public BookItem(String name,String author,Bitmap bitmap){
        bookName = name;
        bookAuthor = author;
        this.bitmap = bitmap;
    }
    public String getBookName(){
        return bookName;
    }
    public String getBookAuthor(){
        return bookAuthor;
    }
    public String getBookChapterURL(){
        return bookChapterURL;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public boolean getBookDownload(){
        return bookDownload;
    }
    public String getBookLocalizationName(){
        return bookLocalizationName;
    }
    public void setBitmap(Bitmap bp){
        bitmap = bp;
    }
    public void setBookName(String name){
        bookName = name;
    }
    public void setBookDownload(boolean is){
        bookDownload = is;
    }
    public void setBookLocalizationName(String path){
        bookLocalizationName = path;
    }
    public void setBookAuthor(String author){
        bookAuthor = author;
    }
    public void setBookChapterURL(String url){
        bookChapterURL = url;
    }
}
