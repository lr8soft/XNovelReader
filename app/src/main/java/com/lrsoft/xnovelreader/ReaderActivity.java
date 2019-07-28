package com.lrsoft.xnovelreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lrsoft.xnovelreader.HTMLAnalysis.ArticleReader;
import com.lrsoft.xnovelreader.HTMLAnalysis.ChapterLoader;
import com.lrsoft.xnovelreader.StorageManager.StorageManager;
import com.lrsoft.xnovelreader.TransmissionMiddleware.ChapterListItem;

import org.w3c.dom.Text;

public class ReaderActivity extends AppCompatActivity {
    public static AppDefaultSetting appDefaultSetting = null;
    private GestureDetector gestureDetector = new GestureDetector(getApplication(), new ReaderGesture());
    public static String nowChapterURL = null;
    public static ReaderActivity tempThis = null;
    public static String bookChapterURL = null;
    private String chapterTitle = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tempThis = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        appDefaultSetting = new AppDefaultSetting(getApplication());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent chapterInfo = getIntent();
        chapterTitle = chapterInfo.getStringExtra("chapterName");
        nowChapterURL = chapterInfo.getStringExtra("chapterURL");
        bookChapterURL = chapterInfo.getStringExtra("bookURL");
        TextView titleView = findViewById(R.id.reader_chapterTitle);
        EditText infoView = findViewById(R.id.reader_chapterInfo);
        ArticleReader reader = new ArticleReader(infoView,nowChapterURL);
        StorageManager storageManager = new StorageManager(getApplication());

        titleView.setText(chapterTitle);
        storageManager.setChapterLastRead(bookChapterURL,nowChapterURL,chapterTitle);
        infoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
        reader.start();
        if(appDefaultSetting.getUseNightMode()){
            infoView.setBackgroundColor(Color.parseColor("#ff000000"));
            infoView.setTextColor(Color.parseColor("#606060"));
            titleView.setBackgroundColor(Color.parseColor("#ff000000"));
            titleView.setTextColor(Color.parseColor("#606060"));
            ReaderActivity.this.setTheme(R.style.AppThemeDark);
            try{
                //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().setStatusBarColor(Color.parseColor("#ff000000"));
            }catch (Exception expt){}
        }
    }
}
class ReaderGesture extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return super.onSingleTapUp(e);
    }@Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub
        super.onLongPress(e);
    }@Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return super.onDown(e);
    }@Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        //turn right
        int FLING_MIN_DISTANCE = ReaderActivity.appDefaultSetting.getSlipDistance();
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE){
            Log.i("onFling: ", "right");
            ChapterListItem lastChapterInfo = ChapterLoader.getNextChapter(ReaderActivity.nowChapterURL);
            String chapterTitle = lastChapterInfo.chapterName;
            String chapterURL = lastChapterInfo.chapterURL;
            Intent intent = new Intent(ReaderActivity.tempThis,ReaderActivity.class);
            intent.putExtra("chapterName", chapterTitle);
            intent.putExtra("chapterURL",chapterURL);
            intent.putExtra("bookURL", ReaderActivity.bookChapterURL);
            ReaderActivity.tempThis.finish();
            ReaderActivity.tempThis.startActivity(intent);
            return true;
        }
        ///turn left
        if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE){
            Log.i("onFling: ", "left");
            ChapterListItem lastChapterInfo = ChapterLoader.getPreviousChapter(ReaderActivity.nowChapterURL);
            String chapterTitle = lastChapterInfo.chapterName;
            String chapterURL = lastChapterInfo.chapterURL;
            Intent intent = new Intent(ReaderActivity.tempThis,ReaderActivity.class);
            intent.putExtra("chapterName", chapterTitle);
            intent.putExtra("chapterURL",chapterURL);
            intent.putExtra("bookURL", ReaderActivity.bookChapterURL);
            ReaderActivity.tempThis.finish();
            ReaderActivity.tempThis.startActivity(intent);
            return true;
        }
        return super.onFling(e1, e2 ,velocityX, velocityY);
    }@Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {
        return super.onScroll(e1, e2, distanceX, distanceY);
    }@Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        // TODO Auto-generated method stub
        return super.onSingleTapConfirmed(e);
    }@Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub
        super.onShowPress(e);
    }

}
