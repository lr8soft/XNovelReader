package com.lrsoft.xnovelreader.BookItem;

import android.graphics.Bitmap;

public class BookItem {
    private Bitmap bitmap;
    private String bookName, authorName;
    private int haveReadPart = 0;
    public BookItem(String name,String author){
        bookName = name;
        authorName = author;
        bitmap = null;
    }
    public BookItem(String name,String author,Bitmap bitmap){
        bookName = name;
        authorName = author;
        this.bitmap = bitmap;
    }
    public String getBookName(){
        return bookName;
    }
    public String getAuthorName(){
        return authorName;
    }
    public int getHaveReadPart(){
        return  haveReadPart;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
}
