package com.lrsoft.xnovelreader.HTMLAnalysis;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.lrsoft.xnovelreader.ChapterDetail.ChapterListAdapter;
import com.lrsoft.xnovelreader.ChapterDetail.ChapterListItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.LogRecord;

public class ChapterLoader extends Thread {
    private ChapterListAdapter callBackAdapter;
    private String chapterURL;
    private AlertDialog.Builder pdialog;
    public ChapterLoader(ChapterListAdapter adapter, String url, AlertDialog.Builder dialog){
        callBackAdapter = adapter;
        chapterURL = url;
        pdialog = dialog;
    }
    @Override
    public void run() {
        Looper.prepare();
        try{
            Document doc = Jsoup.connect(chapterURL).get();
            Elements elements = doc.select("div[class=listmain] > dl > dd");
            int i=0;
            if(!elements.isEmpty()){
                Message msg = mHandler.obtainMessage(2);
                msg.sendToTarget();
            }
            for(Element e:elements){
                String chapterURL = e.select("a").attr("href").toString();
                String chapterName = e.select("a").text();
                ChapterListItem temp = new ChapterListItem();
                temp.chapterID = i;
                temp.chapterName = chapterName;
                temp.chapterURL = chapterURL;
                Message msg = mHandler.obtainMessage(0 ,temp);
                msg.sendToTarget();
                i++;
            }
        }catch (Exception exp){
            Message msgExp = mHandler.obtainMessage(1, exp.getMessage());
            msgExp.sendToTarget();
        }
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                    case 0://handle chapter one by one
                        ChapterListItem item = (ChapterListItem)msg.obj;
                        callBackAdapter.add(item);
                        callBackAdapter.notifyDataSetChanged();
                        break;
                    case 1://exception
                        pdialog.setTitle("加载章节时发生异常！");
                        pdialog.setMessage(msg.obj.toString());
                        pdialog.show();
                        break;
                    case 2:
                        callBackAdapter.clear();
                        break;
            }
        }
    };
}
