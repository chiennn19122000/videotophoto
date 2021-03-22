package com.example.videotophoto.activitys;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.videotophoto.BaseActivity;
import com.example.videotophoto.R;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class GalleryActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void setupListener() {

    }

    @Override
    protected void populateData() {
        PagerAdapter pagerAdapter = new com.example.videotophoto.adapters.PagerAdapter(getSupportFragmentManager(),1);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        callback();

    }
}
