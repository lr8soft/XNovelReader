package com.lrsoft.xnovelreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.lrsoft.xnovelreader.HTMLAnalysis.ArticleReader;

import org.w3c.dom.Text;

public class ReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        Intent chapterInfo = getIntent();
        String title = chapterInfo.getStringExtra("chapterName");
        String url = chapterInfo.getStringExtra("chapterURL");
        TextView titleView = findViewById(R.id.reader_chapterTitle);
        EditText infoView = findViewById(R.id.reader_chapterInfo);
        titleView.setText(title);

        ArticleReader reader = new ArticleReader(infoView,"https://www.biqiuge.com/"+url);
        reader.start();
    }
}
