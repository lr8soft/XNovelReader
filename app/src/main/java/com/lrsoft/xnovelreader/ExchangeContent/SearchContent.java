package com.lrsoft.xnovelreader.ExchangeContent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.lrsoft.xnovelreader.BookDetailDialog;
import com.lrsoft.xnovelreader.MainActivity;
import com.lrsoft.xnovelreader.R;
import com.lrsoft.xnovelreader.SearchButtonContent;
import com.lrsoft.xnovelreader.SearchItem.SearchItem;

public class SearchContent extends Fragment implements AdapterView.OnItemClickListener {
    private MainActivity callbackObj;
    public SearchContent(MainActivity callback){
        callbackObj = callback;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_search_content, container, false);
        ListView list = view.findViewById(R.id.searchpage_searchShowList);
        list.setAdapter(MainActivity.searchListAdapter);
        list.setOnItemClickListener(this);
        Button btnSearch = view.findViewById(R.id.searchpage_searchBtn);
        btnSearch.setOnClickListener(new SearchButtonContent(callbackObj));
        return  view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SearchItem info = MainActivity.searchList.get(i);
        Intent intent = new Intent(getActivity(), BookDetailDialog.class);
        intent.putExtra("bookTitle", info.bookName);
        intent.putExtra("bookAuthor", info.bookAuthor);
        intent.putExtra("bookChapterURL", info.bookChapterURL);
        intent.putExtra("bookImage", info.bookImage);
        startActivity(intent);
    }
}

