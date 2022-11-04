package com.techyasoft.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        PDFView pdfView=findViewById(R.id.pdfViewer);
        if (getIntent()!=null){
            Uri filePath= Uri.parse(getIntent().getExtras().getString("file"));
            pdfView.fromUri(filePath).load();
        }
    }
}