package com.example.videotophoto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.videotophoto.activitys.GalleryActivity;
import com.example.videotophoto.fragments.GetFolderVideoFragment;
import com.example.videotophoto.fragments.GetImageFromAppFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.select_Video)
    LinearLayout selectVideo;

    @BindView(R.id.select_Gallery)
    LinearLayout selectGallery;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupListener() {
        SelectVideo();
        SelectGallery();


    }

    @Override
    protected void populateData() {


    }

    private void SelectVideo(){
        selectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if (askForPermission())
             {
                 fragmentFolder();

             }
            }
        });
    }

    private void SelectGallery()
    {
        selectGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, GalleryActivity.class));
            }
        });
    }

    private boolean askForPermission()
    {

        if (Build.VERSION.SDK_INT >=23)
        {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                return makeRequest();

            }

        }
        return true;
    }

    private boolean makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
        return true;
    }

    private void fragmentFolder()
    {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft_add = fm.beginTransaction();
        ft_add.add(R.id.layout_main,new GetFolderVideoFragment());
        ft_add.addToBackStack(null);
        ft_add.commit();
    }
}


