package com.lrsoft.xnovelreader.HTMLAnalysis;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;

import com.lrsoft.xnovelreader.HTMLAnalysis.SourceAnalysis.SourceAnalysis;
import com.lrsoft.xnovelreader.TransmissionMiddleware.BookItem;
import java.util.List;

public class BookSearch extends Thread{
    private ArrayAdapter<BookItem> updateAdapter;
    private String bookName;
    public BookSearch(ArrayAdapter<BookItem> adapter, String searchName){
        updateAdapter = adapter;
        bookName = searchName;
    }
    @Override
    public void run() {
        Looper.prepare();
        updateSearchResult(bookName);
    }
    public void updateSearchResult(String searchName){
        SourceAnalysis source = new SourceAnalysis();
        source.getBookFromAllSourceOneByOne(searchName, mHandler);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    List<BookItem> list = (List<BookItem>)msg.obj;
                    updateAdapter.clear();
                    for(int i=0; i<list.size(); i++){
                        updateAdapter.add(list.get(i));
                    }
                    updateAdapter.notifyDataSetChanged();
                    break;
                case -1:
                    updateAdapter.clear();
                    break;
                case 1:
                    BookItem item = (BookItem)msg.obj;
                    updateAdapter.add(item);
                    updateAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
