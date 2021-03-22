package com.example.videotophoto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videotophoto.R;
import com.example.videotophoto.classUnits.Videos;
import com.example.videotophoto.interfaces.ClickListener;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context mContext;
    private List<Videos> videosList;

    ClickListener onClickListene;
    public void VideoOnClick (ClickListener onClickListene)
    {
        this.onClickListene = onClickListene;
    }

    public VideoAdapter(Context mContext, List<Videos> videosList) {
        this.mContext = mContext;
        this.videosList = videosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_video, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Videos videos = videosList.get(position);
        holder.name.setText(videos.getName());
        Glide.with(mContext).load(videos.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListene.OnClickVideo(videos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_video);
            image = itemView.findViewById(R.id.img_video);
        }
    }
}
