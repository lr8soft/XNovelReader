package com.lrsoft.xnovelreader.ExchangeContent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.lrsoft.xnovelreader.R;

public class SearchContent extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exchange_search_content, container, false);
        return  view;
    }
}

