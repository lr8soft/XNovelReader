package com.lrsoft.xnovelreader.ExchangeContent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.lrsoft.xnovelreader.AppDefaultSetting;
import com.lrsoft.xnovelreader.BookDetailActivity;
import com.lrsoft.xnovelreader.MainActivity;
import com.lrsoft.xnovelreader.R;
import com.lrsoft.xnovelreader.ReaderActivity;
import com.lrsoft.xnovelreader.TransmissionMiddleware.BookItem;
import com.lrsoft.xnovelreader.TransmissionMiddleware.ChapterListItem;

public class BookListContent extends Fragment {
    private AppDefaultSetting appDefaultSetting;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_booklist_content, container, false);
        ListView bookList = view.findViewById(R.id.exchange_booklist_content_favlist);
        bookList.setAdapter(MainActivity.bookadapter);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookItem info = MainActivity.bookadapter.getItem(i);
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("bookTitle", info.getBookName());
                intent.putExtra("bookAuthor", info.getBookAuthor());
                intent.putExtra("bookChapterURL", info.getBookChapterURL());
                intent.putExtra("bookImage", info.getBitmap());
                startActivity(intent);
            }
        } );
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
