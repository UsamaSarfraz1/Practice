package com.techyasoft.practice;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private View mLayout;
    private static final int PERMISSION_REQUEST_READ = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout=findViewById(R.id.main_layout);
        findViewById(R.id.button_open_camera).setOnClickListener(view -> readFiles());
        findViewById(R.id.browsefiles).setOnClickListener(view -> readFiles());

    }

    private void openPdfFile() {
        // Request code for selecting a PDF document.
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            mStartActivityForResult.launch(intent);
    }

    ActivityResultLauncher<Intent> mStartActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode()== Activity.RESULT_OK){
                    Intent intent=result.getData();
                    if(intent!=null){
                        Uri uri=intent.getData();
                        startActivity(new Intent(MainActivity.this,PdfView.class)
                                        .putExtra("file",uri.toString()));
                        /*  PDFView pdfView=findViewById(R.id.pdfViewer);
                        */
                        /*pdfView.fromUri(uri).load();*/
                        /*getFileMetaData(uri);*/
                    }

                }
            }
    );

    private void getFileMetaData(Uri uri) {
        Cursor cursor=this.getContentResolver()
                .query(uri,null,null,null,null,null);
        try {
            if (cursor!=null && cursor.moveToFirst()){
                int displayIndex=cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex=cursor.getColumnIndex(OpenableColumns.SIZE);


                String displayName = cursor.getString(displayIndex);
                String size=null;
                if (!cursor.isNull(sizeIndex)){
                    size=cursor.getString(sizeIndex);
                }else{
                    size="unknown";
                }

                File file=new File(uri.toString());
                Log.i("file",file.getAbsolutePath());


            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mLayout, R.string.camera_permission_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
                Toast.makeText(this, "reading files..", Toast.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mLayout, R.string.camera_permission_denied,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void requestReadStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Snackbar.make(mLayout,R.string.camera_access_required,Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },PERMISSION_REQUEST_READ);
                }
            }).show();
        }else{
            Snackbar.make(mLayout,R.string.camera_unavailable,Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_READ);
        }
    }

    private void readFiles(){
        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.READ_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED){
            Snackbar.make(mLayout,R.string.camera_permission_available,Snackbar.LENGTH_SHORT).show();
           openPdfFile();
        }else{
            requestReadStoragePermission();
        }
    }

}