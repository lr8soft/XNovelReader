package com.lrsoft.xnovelreader;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lrsoft.xnovelreader.HTMLAnalysis.BookSearch;

public class SearchButtonContent implements View.OnClickListener{
    private MainActivity callbackObj;
    public SearchButtonContent(MainActivity cb){
        callbackObj = cb;
    }
    @Override
    public void onClick(View view){
        TextView input = callbackObj.findViewById(R.id.searchpage_searchTextbox);
        ListView searchList = callbackObj.findViewById(R.id.searchpage_searchShowList);
        if(input!=null){
            String bookName = input.getText().toString();
            if(!bookName.equals("")){
                BookSearch search = new BookSearch(callbackObj.searchListAdapter,bookName);
                search.start();
            }
        }else{
            Toast.makeText(callbackObj.getApplication(),"加载控件异常，可能是正在搜索中，请重试！",Toast.LENGTH_SHORT).show();
        }
    }
}
