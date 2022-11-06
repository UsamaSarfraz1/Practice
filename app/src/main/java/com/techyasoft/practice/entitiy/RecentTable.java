package com.techyasoft.practice.entitiy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recent_table")
public class RecentTable {
    @PrimaryKey
    @ColumnInfo(name="r_id")
    private int id;
    @ColumnInfo(name = "r_uri")
    private String r_uri;

    public RecentTable() {
    }

    public RecentTable(int id, String r_uri) {
        this.id = id;
        this.r_uri = r_uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getR_uri() {
        return r_uri;
    }

    public void setR_uri(String r_uri) {
        this.r_uri = r_uri;
    }
}
