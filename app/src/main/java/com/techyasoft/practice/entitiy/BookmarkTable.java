package com.techyasoft.practice.entitiy;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmark_table",foreignKeys = @ForeignKey(entity = RecentTable.class,
    parentColumns = "r_id",
    childColumns = "r_id",
    onDelete = CASCADE
))
public class BookmarkTable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "b_id")
    private int id;
    @ColumnInfo(name = "r_id")
    private int rid;

    public BookmarkTable(int rid) {
        this.rid = rid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
