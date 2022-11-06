package com.techyasoft.practice.entitiy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "recent_table",indices = {@Index(value = "r_uri",unique = true)})
public class RecentTable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="r_id")
    private int id;

    @ColumnInfo(name = "r_uri")
    private String r_uri;

    public RecentTable(String r_uri) {
        this.r_uri = r_uri;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getR_uri() {
        return r_uri;
    }

    public void setR_uri(String r_uri) {
        this.r_uri = r_uri;
    }
}
