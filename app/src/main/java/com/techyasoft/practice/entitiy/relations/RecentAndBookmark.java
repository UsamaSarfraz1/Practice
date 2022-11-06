package com.techyasoft.practice.entitiy.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.techyasoft.practice.entitiy.BookmarkTable;
import com.techyasoft.practice.entitiy.RecentTable;

public class RecentAndBookmark {
    @Embedded private BookmarkTable bookmark;
    @Relation(
            parentColumn = "r_id",
            entityColumn = "r_id"
    )
    private RecentTable recent;
}
