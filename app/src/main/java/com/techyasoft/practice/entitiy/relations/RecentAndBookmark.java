package com.techyasoft.practice.entitiy.relations;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import com.techyasoft.practice.BookMark;
import com.techyasoft.practice.entitiy.BookmarkTable;
import com.techyasoft.practice.entitiy.RecentTable;

public class RecentAndBookmark {
    @Embedded private RecentTable recent;
    @Relation(
            parentColumn = "r_id",
            entityColumn = "b_r_id"
    )
    private BookmarkTable bookmark;

    public RecentAndBookmark() {
    }

    public void setBookmark(BookmarkTable bookmark) {
        this.bookmark = bookmark;
    }

    public void setRecent(RecentTable recent) {
        this.recent = recent;
    }

}
