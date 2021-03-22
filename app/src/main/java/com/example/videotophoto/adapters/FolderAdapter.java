package com.example.videotophoto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophoto.R;
import com.example.videotophoto.classUnits.Folder;
import com.example.videotophoto.interfaces.ClickListener;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private Context mContext;
    private List<Folder> folderArrayList;
    ClickListener onClickListener ;
    public void FolderOnCLick(ClickListener onClickListener){
        this.onClickListener = onClickListener ;
    }

    public FolderAdapter(Context mContext, List<Folder> folderArrayList) {
        this.mContext = mContext;
        this.folderArrayList = folderArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_folder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Folder folder = folderArrayList.get(position);
        holder.name.setText(folder.getName()+" ("+ folder.getCout()+ " video)");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnClickFolder(folder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_folder);
        }
    }
}