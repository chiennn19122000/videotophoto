package com.example.videotophoto.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophoto.R;
import com.example.videotophoto.classUnits.Emojis;
import com.example.videotophoto.classUnits.Folder;
import com.example.videotophoto.interfaces.ClickListener;

import java.util.List;

public class EmojisAdapter extends RecyclerView.Adapter<EmojisAdapter.ViewHolder>{
    private Context mContext;
    private List<Emojis> emojisList;
    ClickListener onClickListener ;
    public void EmojisOnClick(ClickListener onClickListener){
        this.onClickListener = onClickListener ;
    }

    public EmojisAdapter(Context mContext, List<Emojis> emojisList) {
        this.mContext = mContext;
        this.emojisList = emojisList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_emojis, parent, false);
        EmojisAdapter.ViewHolder viewHolder = new EmojisAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Emojis emojis = emojisList.get(position);
        holder.name.setText(emojis.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnClickEmojis(emojis);
            }
        });
    }

    @Override
    public int getItemCount() {
        return emojisList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.emojis);
        }
    }
}