package com.lrsoft.xnovelreader.SearchItem;

import android.graphics.Bitmap;

public class SearchItem {
    public String bookName;
    public String bookAuthor;
    public String bookIntroduction;
    public String bookChapterURL;
    public Bitmap bookImage;
    /*https://www.biqiuge.com/*/
    /*www.23us.so*/
    /*www.qiushuge.net*/
    public enum AnalysisType {
        biqiuge, dingdiann, dingdian23us
    };
    public AnalysisType HTMLAnalysisType;
}
