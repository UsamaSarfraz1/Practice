package com.techyasoft.practice;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.snackbar.Snackbar;
import com.techyasoft.practice.entitiy.BookmarkTable;
import com.techyasoft.practice.entitiy.RecentTable;
import com.techyasoft.practice.viewmodel.RecentViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private View mLayout;
    private static final int PERMISSION_REQUEST_READ = 0;
    private RecentViewModel mRecentViewModel;
    private final String TAG=MainActivity.class.getSimpleName();
    List<RecentTable> recentlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout=findViewById(R.id.main_layout);
        final Intent intent = getIntent();
        final String action = intent.getAction();

        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = intent.getData();
            PDFView pdfView=findViewById(R.id.pdfViewer);
            pdfView.fromUri(uri).load();
            getFileMetaData(uri);
            Toast.makeText(this,uri.toString(),Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "intent was something else: "+action);
        }

        findViewById(R.id.button_open_camera).setOnClickListener(view ->
        {   mRecentViewModel.insertBookMArk(new BookmarkTable(recentlist.get(1).getId()));});
        findViewById(R.id.browsefiles).setOnClickListener(view -> readFiles());
        mRecentViewModel=new ViewModelProvider(this).get(RecentViewModel.class);
        mRecentViewModel.getRecentPdf().observe(this,recentPdf -> {
            Log.i(TAG,""+recentPdf.size());
            recentlist.addAll(recentPdf);
        });
        findViewById(R.id.bookmark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,BookMark.class));
            }
        });
        findViewById(R.id.btnRecent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecentViewModel.deleteRecent(recentlist.get(1).getId());
                Log.i(TAG,""+recentlist.get(1).getId());
            }
        });
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
                        mRecentViewModel.insertRecent(new RecentTable(uri.toString()));
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
        String[] file = new String[]{MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME};
        Cursor cursor=this.getContentResolver()
                .query(uri,file,null,null,null,null);
        try {
            if (cursor!=null && cursor.moveToFirst()){
                int displayIndex=cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex=cursor.getColumnIndex(OpenableColumns.SIZE);
                int pathIndex= cursor.getColumnIndex(MediaStore.MediaColumns.DATA);

                String displayName = cursor.getString(displayIndex);
             /*   String filePath=cursor.getString(pathIndex);*/
                Log.i(TAG,"display name : "+displayName);

                if (pathIndex!=-1){
                    String path=cursor.getString(pathIndex);
                    Log.i(TAG,"path : "+path);
                }else{
                    Log.i(TAG,"path : "+pathIndex);
                }
                String size=null;
                if (!cursor.isNull(sizeIndex)){
                    size=cursor.getString(sizeIndex);
                }else{
                    size="unknown";
                }
                /*File file=new File(uri.toString());
                Log.i("file",file.getAbsolutePath());*/
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Snackbar.make(mLayout,R.string.camera_access_required,Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },PERMISSION_REQUEST_READ);
                }
            }).show();
        }else{
            Snackbar.make(mLayout,R.string.camera_unavailable,Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_READ);
        }
    }

    private void readFiles(){

        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED){
            Snackbar.make(mLayout,R.string.camera_permission_available,Snackbar.LENGTH_SHORT).show();
           openPdfFile();
        }else{
            requestReadStoragePermission();
        }
    }

}