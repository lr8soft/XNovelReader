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

import com.lrsoft.xnovelreader.StorageManager.StorageManager;
import com.lrsoft.xnovelreader.TransmissionMiddleware.BookItem;
import com.lrsoft.xnovelreader.TransmissionMiddleware.ChapterListAdapter;
import com.lrsoft.xnovelreader.TransmissionMiddleware.ChapterListItem;
import com.lrsoft.xnovelreader.HTMLAnalysis.ChapterLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    private String bookChapterURL,title,author;
    public ChapterListAdapter chapterListAdapter = null;
    public List<ChapterListItem> storageChapterlist = new ArrayList<>();
    private AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_dialog);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (chapterListAdapter == null) {
            chapterListAdapter = new ChapterListAdapter(BookDetailActivity.this, R.layout.chapter_item, storageChapterlist);
        }
        dialog = new AlertDialog.Builder(BookDetailActivity.this);
        onLayoutInit();
    }
    private void onLayoutInit(){
        Intent receiveInfo = getIntent();
        title = receiveInfo.getStringExtra("bookTitle");
        author = receiveInfo.getStringExtra("bookAuthor");
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
                Intent intent = new Intent(BookDetailActivity.this, ReaderActivity.class);
                intent.putExtra("chapterName", info.chapterName);
                intent.putExtra("chapterURL",info.chapterURL);
                intent.putExtra("bookURL",bookChapterURL);
                startActivity(intent);
            }
        });
        ChapterLoader chapterLoader = new ChapterLoader(chapterListAdapter,bookChapterURL,dialog);
        chapterLoader.start();

        Button btnAddToShelf = findViewById(R.id.detail_dialog_addBookBtn);
        Button btnRefresh = findViewById(R.id.detail_dialog_bookRefresh);
        Button btnDownload = findViewById(R.id.detail_dialog_bookDownload);
        Button btnContinue = findViewById(R.id.detail_dialog_continueRead);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChapterLoader chapterLoader = new ChapterLoader(chapterListAdapter,bookChapterURL,dialog);
                chapterLoader.start();
                Toast.makeText(BookDetailActivity.this,"章节加载中，请稍后...",Toast.LENGTH_LONG).show();
                StorageManager storageManager = new StorageManager(getApplication());
                Calendar calendar = Calendar.getInstance();
                String updateTime = "上次刷新: " + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + " "
                        + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
                storageManager.setLastUpdateTime(bookChapterURL,updateTime );
            }
        });
        btnAddToShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageManager storageManager = new StorageManager(getApplication());
                BookItem temp = new BookItem();
                temp.setBookName(title);
                temp.setBookAuthor(author);
                temp.setBookChapterURL(bookChapterURL);
                if(storageManager.addBookToList(temp)){
                    Toast.makeText(BookDetailActivity.this,"已添加到我的书架！",Toast.LENGTH_SHORT).show();
                }else{
                    if(storageManager.removeFromBookList(temp)){
                        Toast.makeText(BookDetailActivity.this,"已从我的书架中删除！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(BookDetailActivity.this,"无法从我的书架中删除本书！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageManager storageManager = new StorageManager(getApplication());
                ChapterListItem lastChapterInfo = storageManager.getChapterLastReadByURL(bookChapterURL);
                String chapterTitle = lastChapterInfo.chapterName;
                String chapterURL = lastChapterInfo.chapterURL;
                Intent intent = new Intent(BookDetailActivity.this, ReaderActivity.class);
                intent.putExtra("chapterName", chapterTitle);
                intent.putExtra("chapterURL",chapterURL);
                intent.putExtra("bookURL",bookChapterURL);
                startActivity(intent);
            }
        });
    }

}
