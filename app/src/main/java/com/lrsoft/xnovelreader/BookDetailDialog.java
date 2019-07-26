package com.lrsoft.xnovelreader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lrsoft.xnovelreader.ChapterDetail.ChapterListAdapter;
import com.lrsoft.xnovelreader.ChapterDetail.ChapterListItem;
import com.lrsoft.xnovelreader.HTMLAnalysis.ChapterLoader;

import java.util.ArrayList;
import java.util.List;

public class BookDetailDialog extends AppCompatActivity {
    private String bookChapterURL;
    public ChapterListAdapter chapterListAdapter = null;
    public List<ChapterListItem> storageChapterlist = new ArrayList<>();
    private AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_dialog);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        if(chapterListAdapter==null){
            chapterListAdapter = new ChapterListAdapter(BookDetailDialog.this,R.layout.chapter_item, storageChapterlist);
        }
        dialog = new AlertDialog.Builder(BookDetailDialog.this);
        onLayoutInit();
    }
    private void onLayoutInit(){
        Intent receiveInfo = getIntent();
        String title = receiveInfo.getStringExtra("bookTitle");
        String author = receiveInfo.getStringExtra("bookAuthor");
        bookChapterURL = receiveInfo.getStringExtra("bookChapterURL");
        Bitmap bitmep = receiveInfo.getParcelableExtra("bookImage");
        TextView authorView = findViewById(R.id.detail_dialog_bookAuthor);
        TextView titleView = findViewById(R.id.detail_dialog_bookTitle);
        ImageView image = findViewById(R.id.detail_dialog_bookImage);
        if(bitmep == null){
            image.setImageResource(R.drawable.default_book);
        }else{
            image.setImageBitmap(bitmep);
        }

        titleView.setText(title);
        authorView.setText(author);

        ListView chapterList = findViewById(R.id.detail_dialog_chapterList);
        chapterList.setAdapter(chapterListAdapter);
        chapterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChapterListItem info = storageChapterlist.get(i);
                Intent intent = new Intent(BookDetailDialog.this, ReaderActivity.class);
                intent.putExtra("chapterName", info.chapterName);
                intent.putExtra("chapterURL",info.chapterURL);
                startActivity(intent);
            }
        });
        ChapterLoader chapterLoader = new ChapterLoader(chapterListAdapter,bookChapterURL,dialog);
        chapterLoader.start();

        Button btnAddToShelf = findViewById(R.id.detail_dialog_addBookBtn);
        Button btnRefresh = findViewById(R.id.detail_dialog_bookRefresh);
        Button btnDownload = findViewById(R.id.detail_dialog_bookDownload);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChapterLoader chapterLoader = new ChapterLoader(chapterListAdapter,bookChapterURL,dialog);
                chapterLoader.start();
                Toast.makeText(BookDetailDialog.this,"章节加载中，请稍后...",Toast.LENGTH_LONG).show();
            }
        });
    }

}
