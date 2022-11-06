package com.techyasoft.practice.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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
    @Insert()
    void insertRecent(RecentTable recent);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBookMark(BookmarkTable bookmarkTable);

    @Query("Select * From recent_table INNER JOIN bookmark_table ON bookmark_table.r_id=recent_table.r_id")
    LiveData<List<RecentTable>> getBookMarkPdf();

    @Query("Select * From recent_table")
    LiveData<List<RecentTable>> getRecentPdf();

    @Query("Delete FROM recent_table WHERE r_id=:r_id")
    void deleteRecentPdf(int r_id);
}
