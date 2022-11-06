package com.techyasoft.practice.entitiy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmark_table")
public class BookmarkTable {
    @PrimaryKey
    @ColumnInfo(name = "b_id")
    private int id;
    @ColumnInfo(name = "r_id")
    private int rid;
}
