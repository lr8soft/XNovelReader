package com.lrsoft.xnovelreader;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lrsoft.xnovelreader.ExchangeContent.BookListContent;
import com.lrsoft.xnovelreader.ExchangeContent.SearchContent;
import com.lrsoft.xnovelreader.ExchangeContent.SettingContent;

public class ToolsButtonContent implements View.OnClickListener{
    private MainActivity callbackClass;
    private static BookListContent bookListContent = null;
    private static SearchContent searchContent = null;
    private  static SettingContent settingContent = null;
    public ToolsButtonContent(MainActivity cb){
        callbackClass = cb;
        defaultWork();
    }
    private void defaultWork(){
        onFavButtonClick();
    }
    private void onFavButtonClick(){
        FragmentManager fragmentManager = callbackClass.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(bookListContent==null){
            bookListContent = new BookListContent();
        }
        transaction.replace(R.id.homepage_detail_content, bookListContent);
        transaction.commit();
        callbackClass.setMode(callbackClass.BookListMode);
        callbackClass.setTitle("我的书架");
    }
    private void onSearchButtonClick(){
        FragmentManager fragmentManager = callbackClass.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(searchContent==null){
            searchContent = new SearchContent(callbackClass);
        }
        transaction.replace(R.id.homepage_detail_content, searchContent);
        transaction.commit();
        callbackClass.setMode(callbackClass.SearchMode);
        callbackClass.setTitle("搜索书籍");
    }
    private void onSettingButtonClick(){
        FragmentManager fragmentManager = callbackClass.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(settingContent==null){
            settingContent = new SettingContent(callbackClass);
        }
        transaction.replace(R.id.homepage_detail_content, settingContent);
        transaction.commit();
        callbackClass.setMode(callbackClass.SettingMode);
        callbackClass.setTitle("应用设置");
    }
    @Override
    public void onClick(View view) {
        int btnId = view.getId();
        ImageButton btnfav = callbackClass.findViewById(R.id.homepage_detail_fav);
        ImageButton btnSearch = callbackClass.findViewById(R.id.homepage_detail_search);
        ImageButton btnSetting = callbackClass.findViewById(R.id.homepage_detail_setting);
        try{
            boolean value = callbackClass.toolBtnList.get(btnId);
            switch (btnId){
                case R.id.homepage_detail_fav:
                    btnfav.setBackgroundResource(R.drawable.fav_btn_selected);
                    btnSearch.setBackgroundResource(R.drawable.search_btn);
                    btnSetting.setBackgroundResource(R.drawable.setting_btn);
                    onFavButtonClick();
                    break;
                case R.id.homepage_detail_search:
                    btnfav.setBackgroundResource(R.drawable.fav_btn);
                    btnSearch.setBackgroundResource(R.drawable.search_btn_selected);
                    btnSetting.setBackgroundResource(R.drawable.setting_btn);
                    onSearchButtonClick();
                    break;
                case R.id.homepage_detail_setting:
                    btnfav.setBackgroundResource(R.drawable.fav_btn);
                    btnSearch.setBackgroundResource(R.drawable.search_btn);
                    btnSetting.setBackgroundResource(R.drawable.setting_btn_selected);
                    onSettingButtonClick();
                    break;
            }
        }catch (Exception exp){
            Log.e("changeButtonView: ",exp.getLocalizedMessage());
        }
    }
}
