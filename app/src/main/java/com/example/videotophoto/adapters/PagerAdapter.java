package com.example.videotophoto.adapters;

import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.videotophoto.fragments.GetFolderVideoFragment;
import com.example.videotophoto.fragments.GetImageFromAppFragment;
import com.example.videotophoto.fragments.GetVideoFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.videotophoto.Constants.REQUEST_URI_FOLDER;

public class PagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {

        super(fm, behavior);
        fragmentList = new ArrayList<>();

        File f = new File(Environment.getExternalStorageDirectory() + "/VideoToPhto/Videos");
        f.mkdirs();

        Bundle bundle = new Bundle();
        bundle.putString(REQUEST_URI_FOLDER,f.getPath());
        GetVideoFragment getVideoFragment = new GetVideoFragment();
        getVideoFragment.setArguments(bundle);

        fragmentList.add(getVideoFragment);
        fragmentList.add(new GetImageFromAppFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Videos";
            case 1:
                return "Images";
            default:
                return "";
        }

    }
}
