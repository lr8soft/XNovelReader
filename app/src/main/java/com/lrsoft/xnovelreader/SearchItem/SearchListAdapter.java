package com.lrsoft.xnovelreader.SearchItem;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lrsoft.xnovelreader.R;

import org.w3c.dom.Text;

import java.util.List;
public class SearchListAdapter  extends ArrayAdapter<SearchItem> {
    private int rid;
    public SearchListAdapter(Context context, int rid, List<SearchItem> list){
        super(context, rid, list);
        this.rid = rid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(rid, parent, false);
        ImageView image = view.findViewById(R.id.searchItemImage);
        TextView title = view.findViewById(R.id.searchItemTitle);
        TextView author = view.findViewById(R.id.searchItemAuthor);
        TextView source = view.findViewById(R.id.searchItemSource);
        if(item.bookImage==null){
            image.setImageResource(R.drawable.default_book);
        }else{
            image.setImageBitmap(item.bookImage);
        }

        title.setText(item.bookName);
        author.setText(item.bookAuthor);
        source.setText(item.bookChapterURL);
        return view;
    }
}
