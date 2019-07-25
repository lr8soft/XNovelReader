package com.lrsoft.xnovelreader;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.lrsoft.xnovelreader.BookItem.BookItem;
import com.lrsoft.xnovelreader.ExchangeContent.BookListAdapter;
import com.lrsoft.xnovelreader.SearchItem.SearchItem;
import com.lrsoft.xnovelreader.SearchItem.SearchListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    public static HashMap<Integer, Boolean> toolBtnList;
    public final int BookListMode = 0, SearchMode = 1, SettingMode = 2;
    public static List<BookItem> booklist = new ArrayList<>();
    public static List<SearchItem> searchList = new ArrayList<>();
    public static BookListAdapter bookadapter = null;
    public static SearchListAdapter searchListAdapter = null;
    private int NowMode = BookListMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        if(bookadapter == null){
            bookadapter = new BookListAdapter(MainActivity.this,R.layout.book_item,booklist);
        }
        toolBtnList = new HashMap<>();
        if(searchListAdapter == null){
            searchListAdapter = new SearchListAdapter(MainActivity.this, R.layout.search_item, searchList);
        }
        onLayoutInit();
    }
    private void onLayoutInit(){
        toolBtnList.put(R.id.homepage_detail_fav,true);//default selected
        toolBtnList.put(R.id.homepage_detail_search,false);
        toolBtnList.put(R.id.homepage_detail_setting,false);

        ImageButton btnfav = findViewById(R.id.homepage_detail_fav);
        ImageButton btnSearch = findViewById(R.id.homepage_detail_search);
        ImageButton btnSetting = findViewById(R.id.homepage_detail_setting);
        btnfav.setOnClickListener(new ToolsButtonContent(this));
        btnSearch.setOnClickListener(new ToolsButtonContent(this));
        btnSetting.setOnClickListener(new ToolsButtonContent(this));

        BookItem item = new BookItem("时间简史","史帝芬·霍金");
        bookadapter.add(item);
        BookItem item2 = new BookItem("克苏鲁神话","霍华德·菲利普斯·洛夫克拉夫特");
        bookadapter.add(item2);
    }

    public int getMode(){
        return  NowMode;
    }
    public void setMode(int mode){
        NowMode = mode;
    }
}
