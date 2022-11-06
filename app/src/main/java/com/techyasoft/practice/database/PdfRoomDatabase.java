package com.techyasoft.practice.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.techyasoft.practice.dao.PdfDao;
import com.techyasoft.practice.entitiy.RecentTable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {RecentTable.class},version = 1, exportSchema = false)
public abstract class PdfRoomDatabase extends RoomDatabase {
    public abstract PdfDao pdfDao();
    private static volatile PdfRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor=
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PdfRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (PdfRoomDatabase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),PdfRoomDatabase.class,"pdf_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
