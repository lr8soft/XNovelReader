package com.lrsoft.xnovelreader.StorageManager;

import android.content.Context;
import android.util.Log;

import com.lrsoft.xnovelreader.TransmissionMiddleware.BookItem;
import com.lrsoft.xnovelreader.TransmissionMiddleware.ChapterListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {
    private final  String storageFileName = "XNovelReader.BookList";
    private JSONObject dataObject = null;
    private JSONArray bookArray = null;
    private Context insideContext;
    public StorageManager(Context context) {
        insideContext = context;
        if(!updateDataFile()){
            createDataFile();
        }
    }
    public List<BookItem> getAllBook(){
        List<BookItem> list = new ArrayList<>();
        if(bookArray!=null && bookArray.length()!=0){
            for(int i=0; i<bookArray.length(); i++){
                try{
                    JSONObject temp = bookArray.getJSONObject(i);
                    BookItem tempBook = new BookItem();
                    String bookName =  temp.optString("bookName");
                    String bookAuthor = temp.optString("bookAuthor");
                    String bookChapterURL = temp.optString("bookChapterURL");
                    String bookLocalizationName = temp.optString("bookLocalizationName");
                    String bookLastRead = temp.optString("bookLastRead");
                    String bookLastReadName = temp.optString("bookLastReadName");
                    boolean bookDownload = temp.optBoolean("bookDownload");
                    tempBook.setBookAuthor(bookAuthor);
                    tempBook.setBookChapterURL(bookChapterURL);
                    tempBook.setBookName(bookName);
                    tempBook.setBookDownload(bookDownload);
                    tempBook.setBookLocalizationName(bookLocalizationName);
                    tempBook.setLastChapterURL(bookLastRead);
                    tempBook.setLastChapterName(bookLastReadName);
                    list.add(tempBook);
                }catch (JSONException exp){
                    continue;
                }
            }
        }
        return list;
    }
    public boolean addBookToList(BookItem bookItem){
        if(!isBookExisted(bookItem.getBookName(),bookItem.getBookChapterURL())){
            if(bookArray!=null){
                JSONObject object = new JSONObject();
                try{
                    object.put("bookName",bookItem.getBookName());
                    object.put("bookAuthor",bookItem.getBookAuthor());
                    object.put("bookChapterURL",bookItem.getBookChapterURL());
                    object.put("bookDownload",bookItem.getBookDownload());
                    object.put("bookLocalizationName",bookItem.getBookLocalizationName());
                    object.put("bookLastRead", bookItem.getLastChapterURL());
                    object.put("bookLastReadName",bookItem.getLastChapterName());
                    bookArray.put(bookArray.length(),object);
                    if(saveDataChange())
                        return true;
                    else
                        return false;
                }catch (JSONException exp){
                    return false;
                }
            }else{
                return false;
            }
        }else {
            return false;
        }
    }
    public ChapterListItem getChapterLastReadByURL(String bookAllChapterUrl){
        ChapterListItem lastRead = null;
        if(bookArray!=null){
            for(int i=0; i<bookArray.length(); i++){
                try{
                    JSONObject temp = bookArray.getJSONObject(i);
                    String tempURL = temp.optString("bookChapterURL");
                    String tempLastChapter = temp.optString("bookLastRead");
                    String tempLastChapterName = temp.optString("bookLastReadName");
                    if(tempURL.equals(bookAllChapterUrl)){
                        lastRead = new ChapterListItem();
                        lastRead.chapterName = tempLastChapterName;
                        lastRead.chapterURL = tempLastChapter;
                    }
                }catch (JSONException exp){
                    continue;
                }
            }
        }
        return lastRead;
    }
    public boolean setChapterLastRead(String bookAllChapterUrl, String nowChapterURL, String nowChapterName){
        if(bookArray!=null){
            for(int i=0; i<bookArray.length(); i++){
                try{
                    JSONObject temp = bookArray.getJSONObject(i);
                    String tempName = temp.optString("bookName");
                    String tempURL = temp.optString("bookChapterURL");
                    String tempLastChapter = temp.optString("bookLastRead");
                    if(tempURL.equals(bookAllChapterUrl)){
                        temp.put("bookLastRead", nowChapterURL);
                        temp.put("bookLastReadName", nowChapterName);
                        if(saveDataChange()){
                            return true;
                        }
                        else
                            return false;
                    }
                }catch (JSONException exp){
                    Log.i("setChapterLastRead: ", exp.getLocalizedMessage());
                    continue;
                }
            }
            return false;
        }else{
            return false;
        }
    }
    public boolean isBookExisted(String bookName, String url){
        if(bookArray!=null){
            if(bookArray.length()==0){
                return false;
            }else{
                for(int i=0; i<bookArray.length(); i++){
                    try{
                        JSONObject temp = bookArray.getJSONObject(i);
                        String tempName = temp.optString("bookName");
                        String tempURL = temp.optString("bookChapterURL");
                        if(tempName.equals(bookName) && tempURL.equals(url)){
                            return true;
                        }
                    }catch (JSONException exp){
                        continue;
                    }
                }
                return false;
            }
        }else{
            return false;
        }
    }
    private boolean updateDataFile(){
        FileInputStream is;
        BufferedReader reader;
        try{
            is = insideContext.openFileInput(storageFileName);
            reader = new BufferedReader(new InputStreamReader(is));
            String temp="";String buffer="";
            while((temp=reader.readLine())!=null){
                buffer += temp;
            }
            Log.i("updateDataFile: ",buffer);
            reader.close();
            dataObject = new JSONObject(buffer);
            bookArray = dataObject.getJSONArray("bookItems");
            return true;
        }catch (Exception exp){
            return false;
        }
    }
    private boolean saveDataChange(){
        FileOutputStream os = null;
        BufferedWriter writer = null;
        try{
            os = insideContext.openFileOutput(storageFileName,Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(os));
            if(dataObject!=null){
                writer.write(dataObject.toString());
                writer.close();
                return true;
            }else{
                writer.close();
                return false;
            }
        }catch (IOException exp){
            Log.e("saveDataChange: ",exp.getLocalizedMessage());
            return false;
        }
    }
    private void createDataFile(){
        FileOutputStream os = null;
        BufferedWriter writer = null;
        try{
            os = insideContext.openFileOutput(storageFileName,Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(os));
            dataObject = new JSONObject();
            bookArray = new JSONArray();
            dataObject.put("bookItems",bookArray);
            writer.write(dataObject.toString());
            writer.close();
        }catch (IOException |JSONException exp){
            Log.e("createDataFile: ",exp.getLocalizedMessage());
        }
    }
}
