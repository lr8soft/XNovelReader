package com.lrsoft.xnovelreader.HTMLAnalysis;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.appcompat.app.AlertDialog;

import com.lrsoft.xnovelreader.HTMLAnalysis.SourceAnalysis.SourceAnalysis;
import com.lrsoft.xnovelreader.TransmissionMiddleware.ChapterListAdapter;
import com.lrsoft.xnovelreader.TransmissionMiddleware.ChapterListItem;

import java.util.ArrayList;
import java.util.List;

public class ChapterLoader extends Thread {
    private ChapterListAdapter callBackAdapter;
    private String chapterURL;
    private AlertDialog.Builder pdialog;
    private static List<ChapterListItem> chapterList = null;
    public ChapterLoader(ChapterListAdapter adapter, String url, AlertDialog.Builder dialog){
        callBackAdapter = adapter;
        chapterURL = url;
        pdialog = dialog;
        chapterList = new ArrayList<>();
    }
    @Override
    public void run() {
        Looper.prepare();
        SourceAnalysis source = new SourceAnalysis();
            try {
                List<ChapterListItem> list  = AnalysisChapters(source);
                if(list!=null){
                    for(int i=0; i<list.size(); i++){
                        chapterList.add(list.get(i));
                        Message msg = mHandler.obtainMessage(0 ,list.get(i));
                        msg.sendToTarget();
                    }
                }
            }catch (Exception exp){
                Message msgExp = mHandler.obtainMessage(1, exp.getMessage());
                msgExp.sendToTarget();
            }
    }
    private List<ChapterListItem> AnalysisChapters(SourceAnalysis source) throws Exception{
        List<ChapterListItem> list = null;
        if(chapterURL.contains("biqiuge")){
            list = source.getChapterListFromSource(chapterURL,SourceAnalysis.WebSiteSource.Biquge);
        }else if(chapterURL.contains("dingdiann")){
            list = source.getChapterListFromSource(chapterURL,SourceAnalysis.WebSiteSource.Dingdiann);
        }
        return list;
    }
    public static ChapterListItem getNextChapter(String nowChapter){
        if(chapterList!=null){
            for(int i=0; i<chapterList.size(); i++){
                ChapterListItem temp = chapterList.get(i);
                if(temp.chapterURL.equals(nowChapter)){
                    if(i + 1<chapterList.size()){//isn't the last chapter
                        return chapterList.get(i + 1);
                    }
                }
            }
        }
        return  null;
    }
    public static ChapterListItem getPreviousChapter(String nowChapter){
        if(chapterList!=null){
            for(int i=0; i<chapterList.size(); i++){
                ChapterListItem temp = chapterList.get(i);
                if(temp.chapterURL.equals(nowChapter)){
                    if(i - 1 >=0){//isn't the first chapter
                        return chapterList.get(i - 1);
                    }
                }
            }
        }
        return  null;
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
