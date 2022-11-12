package com.techyasoft.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

public class PdfView extends AppCompatActivity implements OnPageChangeListener {

    int pageNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        setContentView(R.layout.activity_pdf_view);

        PDFView pdfView=findViewById(R.id.pdfViewer);
       
        if (getIntent()!=null){
            Uri filePath= Uri.parse(getIntent().getExtras().getString("file"));
            pdfView.fromUri(filePath)
                    .pageFitPolicy(FitPolicy.BOTH)
                    .defaultPage(pageNum)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            Log.i("Pdfview",""+pdfView.getPageCount());
                        }
                    })
                    .load();

            pdfView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pdfView.jumpTo(3,true);


                }
            });

        }
    }



    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNum=page;
    }
}