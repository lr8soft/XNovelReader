package com.lrsoft.xnovelreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.lrsoft.xnovelreader.StorageManager.StorageManager;
import com.lrsoft.xnovelreader.TransmissionMiddleware.BookItem;
import com.lrsoft.xnovelreader.TransmissionMiddleware.BookListAdapter;
import com.lrsoft.xnovelreader.TransmissionMiddleware.SearchListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    public static HashMap<Integer, Boolean> toolBtnList;
    public final int BookListMode = 0, SearchMode = 1, SettingMode = 2;
    public static List<BookItem> booklist = new ArrayList<>();
    public static List<BookItem> searchList = new ArrayList<>();
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        StorageManager storageManager = new StorageManager(getApplication());
        List<BookItem> ret = storageManager.getAllBook();
        if(!ret.isEmpty()){
            bookadapter.clear();
            bookadapter.addAll(ret);
            bookadapter.notifyDataSetChanged();
        }
    }

    public int getMode(){
        return  NowMode;
    }
    public void setMode(int mode){
        NowMode = mode;
    }
}
