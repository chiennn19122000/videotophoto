package com.example.videotophoto.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophoto.R;
import com.example.videotophoto.activitys.EditImageActivity;
import com.example.videotophoto.adapters.ImageAdapter;
import com.example.videotophoto.classUnits.Emojis;
import com.example.videotophoto.classUnits.Folder;
import com.example.videotophoto.classUnits.Images;
import com.example.videotophoto.classUnits.Videos;
import com.example.videotophoto.interfaces.ClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.videotophoto.Constants.REQUESTCODE;
import static com.example.videotophoto.Constants.REQUEST_URI_IMAGE;
import static com.example.videotophoto.Constants.RESULTCODE;
import static com.example.videotophoto.Constants.RETURNDATA;

public class GetImageFromAppFragment extends Fragment {

    private List<Images> imagesList;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_image, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_image);
        imagesList = new ArrayList<>();

        File f = new File(Environment.getExternalStorageDirectory() + "/VideoToPhto/Images");
        f.mkdirs();
        getAllImage(f);

        imageAdapter = new ImageAdapter(getContext(), imagesList);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        openImage();
        return view;
    }

    private void getAllImage(File file){
        File list[] = file.listFiles();

        for (int i = 0; i < list.length ; i++) {

            if (list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".jpg")){
                this.imagesList.add(new Images(list[i].getName(),list[i].getPath(),list[i].getPath()));
            }
        }

    }
    private void openImage()
    {
        imageAdapter.imageOnClick(new ClickListener() {
            @Override
            public void OnClickFolder(Folder folder) {

            }

            @Override
            public void OnClickVideo(Videos videos) {

            }

            @Override
            public void OnClickImage(Images images) {

                Bundle bundle = new Bundle();
                bundle.putString(REQUEST_URI_IMAGE,images.getUri());

                Intent intent = new Intent(getActivity(), EditImageActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUESTCODE);
            }

            @Override
            public void OnClickEmojis(Emojis emojis) {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUESTCODE && resultCode == RESULTCODE)
        {
            String s = data.getStringExtra(RETURNDATA);
            imagesList.clear();
            File f = new File(Environment.getExternalStorageDirectory() + "/VideoToPhto/Images");
            f.mkdirs();
            getAllImage(f);

            imageAdapter = new ImageAdapter(getContext(), imagesList);
            recyclerView.setAdapter(imageAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

            openImage();
        }
    }

}
