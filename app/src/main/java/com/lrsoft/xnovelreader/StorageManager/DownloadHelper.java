package com.lrsoft.xnovelreader.StorageManager;

import com.lrsoft.xnovelreader.HTMLAnalysis.ArticleReader;
import com.lrsoft.xnovelreader.HTMLAnalysis.SourceAnalysis.SourceAnalysis;

import java.io.BufferedWriter;
import java.util.List;

public class DownloadHelper extends Thread {
    private Float downloadPercent = new Float(0.0f);
    private List<String> chapterURLList;
    private boolean multiThread = false;
    private String bookUUID;
    public DownloadHelper(String bookuid, List<String> chapterURLList, boolean useMultiThread){
        this.chapterURLList = chapterURLList;
        multiThread = useMultiThread;
        bookUUID = bookuid;
    }
    @Override
    public void run() {
        SourceAnalysis sourceAnalysis = new SourceAnalysis();
        if(!multiThread){
            for(int i=0; i<chapterURLList.size(); i++){
                try{
                    String singleChapterInfo = ArticleReader.getArticleFromSoucrce(chapterURLList.get(i),sourceAnalysis);
                }catch (Exception expt){
                    continue;
                }
                updateDownloadPercent((i + 1.0f)/10.0f);
            }
        }
    }
    private synchronized void updateDownloadPercent(float setN){
        downloadPercent = setN;
    }
    public synchronized Float getDownloadPercent(){
        return  downloadPercent;
    }
}
