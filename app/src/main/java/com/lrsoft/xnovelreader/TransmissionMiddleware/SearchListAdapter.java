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
public class SearchListAdapter  extends ArrayAdapter<BookItem> {
    private int rid;
    public SearchListAdapter(Context context, int rid, List<BookItem> list){
        super(context, rid, list);
        this.rid = rid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(rid, parent, false);
        ImageView image = view.findViewById(R.id.searchItemImage);
        TextView title = view.findViewById(R.id.searchItemTitle);
        TextView author = view.findViewById(R.id.searchItemAuthor);
        TextView source = view.findViewById(R.id.searchItemSource);
        if(item.getBitmap()==null){
            image.setImageResource(R.drawable.default_book);
        }else{
            image.setImageBitmap(item.getBitmap());
        }

        title.setText(item.getBookName());
        author.setText(item.getBookAuthor());
        source.setText(item.getBookChapterURL());
        return view;
    }
}
