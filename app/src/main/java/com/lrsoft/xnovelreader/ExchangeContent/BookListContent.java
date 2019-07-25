package com.lrsoft.xnovelreader.ExchangeContent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lrsoft.xnovelreader.BookItem.BookItem;
import com.lrsoft.xnovelreader.MainActivity;
import com.lrsoft.xnovelreader.R;
import com.lrsoft.xnovelreader.ToolsButtonContent;

import java.util.ArrayList;
import java.util.List;

public class BookListContent extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_booklist_content, container, false);
        ListView bookList = view.findViewById(R.id.exchange_booklist_content_favlist);
        bookList.setAdapter(MainActivity.bookadapter);

        return  view;
    }
}
