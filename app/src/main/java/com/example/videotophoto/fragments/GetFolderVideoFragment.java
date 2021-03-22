package com.example.videotophoto.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.videotophoto.R;
import com.example.videotophoto.classUnits.Emojis;
import com.example.videotophoto.classUnits.Folder;
import com.example.videotophoto.classUnits.Images;
import com.example.videotophoto.classUnits.Videos;
import com.example.videotophoto.adapters.FolderAdapter;
import com.example.videotophoto.interfaces.ClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.example.videotophoto.Constants.REQUEST_URI_FOLDER;

public class GetFolderVideoFragment extends Fragment {
    private List<Folder> listFolder;
    private RecyclerView recyclerView;
    private FolderAdapter folderAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_folder_video, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_folder);

        new MyAsynTask().execute();

        return view;
    }

    private void getAllStuff(File file, String name){
        File list[] = file.listFiles();
        boolean folderFound = false;
        File mfile = null;
        String directoryName = "" ;
        int n = 0;

        for (int i = 0; i < list.length ; i++) {

            mfile = new File(file,list[i].getName());

            if (mfile.isDirectory()){
                directoryName = list[i].getName();
                getAllStuff(mfile,directoryName);
            } else {

                if (list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".mp4")){
                    folderFound = true;
                    n++;
                }
            }
        }
        if (folderFound)
        {
            this.listFolder.add(new Folder(name,file.toString(),n));

        }

    }
    private void getAllFolder(){
        String root_sd = Environment.getExternalStorageDirectory().toString();
        File file = new File(root_sd);
        getAllStuff(file,"");
    }

    private void FilterList(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Set<Folder> folderSet = listFolder.stream().collect(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(Folder::getName))));
            listFolder.clear();
            folderSet.forEach(d -> listFolder.add(d));
        }
    }



    private void openFolder(){
        folderAdapter.FolderOnCLick(new ClickListener() {
            @Override
            public void OnClickFolder(Folder folder) {

                 Bundle bundle = new Bundle();
                 bundle.putString(REQUEST_URI_FOLDER,folder.getUri());

                FragmentManager fm = getFragmentManager();

// add
                GetVideoFragment getVideoFragment = new GetVideoFragment();
                getVideoFragment.setArguments(bundle);

                FragmentTransaction ft_rep = fm.beginTransaction();
                ft_rep.replace(R.id.layout_main, getVideoFragment);
                ft_rep.addToBackStack(null);
                ft_rep.commit();

            }

            @Override
            public void OnClickVideo(Videos videos) {

            }

            @Override
            public void OnClickImage(Images images) {

            }

            @Override
            public void OnClickEmojis(Emojis emojis) {

            }


        });
    }

    private class MyAsynTask extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog mProgressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listFolder = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getAllFolder();
            FilterList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            folderAdapter = new FolderAdapter(getContext(), listFolder);
            recyclerView.setAdapter(folderAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            openFolder();
        }


    }

}