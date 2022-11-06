package com.techyasoft.practice.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.techyasoft.practice.dao.PdfDao;
import com.techyasoft.practice.database.PdfRoomDatabase;
import com.techyasoft.practice.entitiy.BookmarkTable;
import com.techyasoft.practice.entitiy.RecentTable;
import com.techyasoft.practice.entitiy.relations.RecentAndBookmark;

import java.util.List;

public class PdfRepository {
    private PdfDao mPdfDao;
    private LiveData<List<RecentTable>> recentPdf;
    private LiveData<List<RecentAndBookmark>> bookMarkPdf;

    public PdfRepository(Application application){
        PdfRoomDatabase db=PdfRoomDatabase.getDatabase(application);
        mPdfDao=db.pdfDao();
        recentPdf=mPdfDao.getRecentPdf();
        bookMarkPdf=mPdfDao.getBookMarkPdf();
    }

    public LiveData<List<RecentTable>> getRecentPdf(){
        return recentPdf;
    }

    LiveData<List<RecentAndBookmark>> getBookMarkPdf(){
        return bookMarkPdf;
    }
    public void insertRecentPdf(RecentTable recent){
        PdfRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mPdfDao.insertRecent(recent);
            }
        });
    }

    public void insertBookmark(BookmarkTable bookmark){
        PdfRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mPdfDao.insertBookMark(bookmark);
            }
        });
    }

}
