package com.techyasoft.practice.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.techyasoft.practice.database.PdfRoomDatabase;
import com.techyasoft.practice.entitiy.BookmarkTable;
import com.techyasoft.practice.entitiy.RecentTable;
import com.techyasoft.practice.entitiy.relations.RecentAndBookmark;

import java.util.List;

@Dao
public interface PdfDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecent(RecentTable recent);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookMark(BookmarkTable bookmarkTable);

    @Transaction
    @Query("Select * From recent_table")
    LiveData<List<RecentAndBookmark>> getBookMarkPdf();

    @Query("Select * From recent_table")
    LiveData<List<RecentTable>> getRecentPdf();
}
