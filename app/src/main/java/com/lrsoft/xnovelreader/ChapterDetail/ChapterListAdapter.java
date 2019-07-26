package com.lrsoft.xnovelreader.ChapterDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrsoft.xnovelreader.BookItem.BookItem;
import com.lrsoft.xnovelreader.R;

import java.util.List;

public class ChapterListAdapter extends ArrayAdapter<ChapterListItem> {
    private int rid;
    public ChapterListAdapter(Context context, int rid, List<ChapterListItem> list){
        super(context, rid, list);
        this.rid = rid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChapterListItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(rid, parent, false);
        TextView chapterName = view.findViewById(R.id.chapter_item_chaptername);
        chapterName.setText(item.chapterName);
        return view;
    }
}
