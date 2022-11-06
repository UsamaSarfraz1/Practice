package com.techyasoft.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.techyasoft.practice.viewmodel.BookmarkViewModel;

public class BookMark extends AppCompatActivity {
    private final String TAG=BookMark.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        BookmarkViewModel bookmarkViewModel=new ViewModelProvider(this).get(BookmarkViewModel.class);
        bookmarkViewModel.getGetRecentAndBookmark().observe(this,recentAndBookmarks -> {
            Log.i(TAG,""+recentAndBookmarks.size());
        });
    }
}