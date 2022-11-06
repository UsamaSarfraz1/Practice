package com.techyasoft.practice.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.techyasoft.practice.BookMark;
import com.techyasoft.practice.entitiy.RecentTable;
import com.techyasoft.practice.entitiy.relations.RecentAndBookmark;
import com.techyasoft.practice.repository.PdfRepository;

import java.util.List;

public class BookmarkViewModel extends AndroidViewModel {
    private final LiveData<List<RecentTable>> getRecentAndBookmark;
    private final PdfRepository pdfRepository;
    public BookmarkViewModel(@NonNull Application application) {
        super(application);
        pdfRepository=new PdfRepository(application);
        getRecentAndBookmark=pdfRepository.getBookMarkPdf();
    }

    public LiveData<List<RecentTable>> getGetRecentAndBookmark() {
        return getRecentAndBookmark;
    }


}
