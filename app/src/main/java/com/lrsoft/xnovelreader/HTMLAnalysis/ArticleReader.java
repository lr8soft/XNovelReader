package com.lrsoft.xnovelreader.HTMLAnalysis;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

public class ArticleReader extends Thread{
    private String chapterURL;
    private TextView callbackTV;
    public ArticleReader(TextView cb,String url){
        chapterURL = url;
        callbackTV = cb;
    }
    @Override
    public void run() {
        try{
            loadFromURL();
        }catch (Exception exp){
            try {
                loadFromURL();//gabbage code 屑代码 都怪傻逼笔趣阁，第一次刷新还不行
            }catch (Exception exp2){
                Log.e("run: ",exp.toString());
                String errinfo = "章节加载失败！请尝试刷新页面！\n"+exp.getMessage();
                Message msg = mHandler.obtainMessage(0, errinfo);
                msg.sendToTarget();
            }
        }

    }
    private void loadFromURL() throws Exception{
        Document doc = Jsoup.connect(chapterURL).get();
        Elements articleInfo = doc.select("div[class=book reader] > div[class=content] > div[class=showtxt]");
        String chapterInfo = "";
        for(Element e:articleInfo){
            String temp = e
                    .html()
                    .replace("&nbsp;"," ")
                    .replace("<br>","\n");
            if(!temp.equals("")){
                chapterInfo+= temp;
            }
        }
        Message msg = mHandler.obtainMessage(0, chapterInfo);
        msg.sendToTarget();
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
