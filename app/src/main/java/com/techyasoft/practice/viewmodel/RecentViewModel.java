package com.techyasoft.practice.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.techyasoft.practice.entitiy.RecentTable;
import com.techyasoft.practice.repository.PdfRepository;

import java.util.List;

public class RecentViewModel extends AndroidViewModel {
    private final PdfRepository mRepository;
    private final LiveData<List<RecentTable>> mRecentPdf;
    public RecentViewModel(@NonNull Application application) {
        super(application);
        mRepository=new PdfRepository(application);
        mRecentPdf=mRepository.getRecentPdf();
    }

    public LiveData<List<RecentTable>> getRecentPdf() {
        return mRecentPdf;
    }

    public void insertRecent(RecentTable recent){mRepository.insertRecentPdf(recent);}

}
