package com.techyasoft.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private View mLayout;
    private static final int PERMISSION_REQUEST_READ = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout=findViewById(R.id.main_layout);
        findViewById(R.id.button_open_camera).setOnClickListener(view -> readFiles());

        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppbar);
        MaterialShapeDrawable bottomBarBackground = (MaterialShapeDrawable) bottomAppBar.getBackground();
     
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
            Toast.makeText(this, "File Reading...", Toast.LENGTH_SHORT).show();
        }else{
            requestReadStoragePermission();
        }
    }

}