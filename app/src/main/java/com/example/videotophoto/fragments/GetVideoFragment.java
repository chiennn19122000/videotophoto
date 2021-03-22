package com.example.videotophoto.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videotophoto.R;
import com.example.videotophoto.classUnits.Emojis;
import com.example.videotophoto.classUnits.Folder;
import com.example.videotophoto.classUnits.Images;
import com.example.videotophoto.classUnits.Videos;
import com.example.videotophoto.activitys.CaptureVideoActivity;
import com.example.videotophoto.adapters.VideoAdapter;
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
import static com.example.videotophoto.Constants.REQUEST_URI_VIDEO;


public class GetVideoFragment extends Fragment {


    private List<Videos> videosList;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String uri = bundle.getString(REQUEST_URI_FOLDER);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_video, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_video);
        videosList = new ArrayList<>();
        getAllVideo(uri);
        FilterList();
        videoAdapter = new VideoAdapter(getContext(), videosList);
        recyclerView.setAdapter(videoAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        openVideo();
        return view;
    }

    private void getAllStuff(File file, String name){
        File list[] = file.listFiles();

        for (int i = 0; i < list.length ; i++) {

                if (list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".mp4")){
                    videosList.add(new Videos(list[i].getPath(),list[i].getName(),list[i].getPath()));
                        }
                    }

    }
    private void getAllVideo(String uri){
        File file = new File(uri);
        getAllStuff(file,"");
    }

    private void FilterList(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Set<Videos> folderSet = videosList.stream().collect(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(Videos::getName))));
            videosList.clear();
            folderSet.forEach(d -> videosList.add(d));
        }
    }
    private void openVideo()
    {
        videoAdapter.VideoOnClick(new ClickListener() {
            @Override
            public void OnClickFolder(Folder folder) {

            }

            @Override
            public void OnClickVideo(Videos videos) {
                Bundle bundle = new Bundle();
                bundle.putString(REQUEST_URI_VIDEO,videos.getUri());
                Intent intent = new Intent(getActivity(), CaptureVideoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void OnClickImage(Images images) {

            }

            @Override
            public void OnClickEmojis(Emojis emojis) {

            }
        });
    }
}