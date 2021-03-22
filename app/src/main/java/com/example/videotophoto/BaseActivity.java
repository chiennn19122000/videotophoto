package com.example.videotophoto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.MenuItem;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    public static final int READ_WRITE_STORAGE = 52;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);

        setupListener();
        populateData();
    }


    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void setupListener();

    protected abstract void populateData();

    protected void callback()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showLoading(@NonNull String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public boolean requestPermission(String permission) {
        boolean isGranted = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{permission},
                    READ_WRITE_STORAGE);
        }
        return isGranted;
    }
}
