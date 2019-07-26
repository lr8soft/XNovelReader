package com.lrsoft.xnovelreader.HTMLAnalysis;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;
import com.lrsoft.xnovelreader.SearchItem.SearchItem;

import java.util.List;

public class BookSearch extends Thread{
    private ArrayAdapter<SearchItem> updateAdapter;
    private String bookName;
    public BookSearch(ArrayAdapter<SearchItem> adapter,String searchName){
        updateAdapter = adapter;
        bookName = searchName;
    }
    @Override
    public void run() {
        Looper.prepare();
        NovelWebsites web = new NovelWebsites(bookName);
        List<SearchItem> list = web.getSearchResult();
        if(!list.isEmpty()){
            Message msg = mHandler.obtainMessage(0, list);
            msg.sendToTarget();
        }
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    List<SearchItem> list = (List<SearchItem>)msg.obj;
                    updateAdapter.clear();
                    for(int i=0; i<list.size(); i++){
                        updateAdapter.add(list.get(i));
                    }
                    updateAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
