package com.lrsoft.xnovelreader.HTMLAnalysis;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lrsoft.xnovelreader.HTMLAnalysis.SourceAnalysis.SourceAnalysis;
public class ArticleReader extends Thread{
    private String chapterURL;
    private TextView callbackTV;
    public ArticleReader(TextView cb,String url){
        chapterURL = url;
        callbackTV = cb;
    }
    @Override
    public void run() {
        SourceAnalysis source = new SourceAnalysis();
        try{
            String info = null;
            if(chapterURL.contains("biqiuge")){
                info = source.getArticleFromSource(chapterURL,SourceAnalysis.WebSiteSource.Biquge);
                Message msg = mHandler.obtainMessage(0, info);
                msg.sendToTarget();
            }else  if(chapterURL.contains("dingdiann")){
                info = source.getArticleFromSource(chapterURL,SourceAnalysis.WebSiteSource.Dingdiann);
                Message msg = mHandler.obtainMessage(0, info);
                msg.sendToTarget();
            }
        }catch (Exception exp){
            Log.e("run: ",exp.toString());
            String errinfo = "章节加载失败！请尝试刷新页面！\n"+exp.getMessage();
            Message msg = mHandler.obtainMessage(0, errinfo);
            msg.sendToTarget();
        }
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    callbackTV.setText(msg.obj.toString());
                    break;
            }
        }
    };
}
