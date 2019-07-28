package com.lrsoft.xnovelreader.TransmissionMiddleware;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrsoft.xnovelreader.TransmissionMiddleware.BookItem;
import com.lrsoft.xnovelreader.R;

import java.util.List;

public class BookListAdapter extends ArrayAdapter<BookItem> {
    private int rid;
    public BookListAdapter(Context context, int rid, List<BookItem> list){
        super(context, rid, list);
        this.rid = rid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(rid, parent, false);
        ImageView image = view.findViewById(R.id.bookImage);
        TextView title = view.findViewById(R.id.bookTitle);
        TextView author = view.findViewById(R.id.bookAuthor);
        TextView lastRead = view.findViewById(R.id.bookHavedRead);
        TextView updateTime = view.findViewById(R.id.bookUpdateTime);
        if(item.getBitmap()==null){
            image.setImageResource(R.drawable.default_book);
        }else{
            image.setImageBitmap(item.getBitmap());
        }
        title.setText(item.getBookName());
        author.setText(item.getBookAuthor());
        if(item.getLastRefreshTime().isEmpty()){
            updateTime.setText("从未刷新");
        }else {
            updateTime.setText(item.getLastRefreshTime());
        }

        if(item.getLastChapterName().equals("")){
            lastRead.setText("仍未读过");
        }else{
            lastRead.setText(item.getLastChapterName());
        }
        return view;
    }
}
