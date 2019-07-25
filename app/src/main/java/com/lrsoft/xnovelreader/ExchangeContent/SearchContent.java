package com.lrsoft.xnovelreader.ExchangeContent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.lrsoft.xnovelreader.MainActivity;
import com.lrsoft.xnovelreader.R;
import com.lrsoft.xnovelreader.SearchButtonContent;

public class SearchContent extends Fragment {
    private MainActivity callbackObj;
    public SearchContent(MainActivity callback){
        callbackObj = callback;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_search_content, container, false);
        ListView list = view.findViewById(R.id.searchpage_searchShowList);
        list.setAdapter(MainActivity.searchListAdapter);

        Button btnSearch = view.findViewById(R.id.searchpage_searchBtn);
        btnSearch.setOnClickListener(new SearchButtonContent(callbackObj));
        return  view;
    }
}

