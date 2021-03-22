package com.example.videotophoto.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videotophoto.R;
import com.example.videotophoto.classUnits.Images;
import com.example.videotophoto.interfaces.ClickListener;
import com.example.videotophoto.interfaces.ClickLongListener;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{

    private Context mContext;
    private List<Images> listimages;

    private boolean checkListener;
    ClickListener onClickListener;
    ClickLongListener onClickLongListener;
    public void imageOnLongClick(ClickLongListener clickLongListener){
        this.onClickLongListener = clickLongListener;
    }
    public void imageOnClick(ClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    public ImageAdapter(Context mContext, List<Images> listimages) {
        this.mContext = mContext;
        this.listimages = listimages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Images images = listimages.get(position);
        holder.name.setText(images.getName());
        Glide.with(mContext).load(images.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListener = true;
                onClickListener.OnClickImage(images);
            }
        });



    }


    @Override
    public int getItemCount() {
        return listimages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private ImageView image;
        private ImageView check;

        public ViewHolder(@NonNull View itemView)

        {
            super(itemView);
            name = itemView.findViewById(R.id.name_img);
            image = itemView.findViewById(R.id.img_view);
            check = itemView.findViewById(R.id.check_img);


        }
    }

}
