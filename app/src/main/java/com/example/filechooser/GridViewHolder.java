package com.example.filechooser;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class GridViewHolder extends AbsViewHolder {

    public GridViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        this.title = view.findViewById(R.id.title);
        this.size = view.findViewById(R.id.size);
        this.icon = view.findViewById(R.id.icon);
        this.checkBox = view.findViewById(R.id.layout_file_select);
    }

    @Override
    public void setData(File data) {
        if(data.isDirectory()){
            this.icon.setImageResource(R.drawable.ic_folder_open_black_24dp);
        } else{
            this.icon.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
        }
        this.title.setText(data.getName());
        this.size.setText(data.isDirectory() && data.listFiles() != null? ""+data.listFiles().length:""+0);
    }

    @Override
    public void setEditMode(boolean isEditMode) {
        if(isEditMode){
            this.checkBox.setVisibility(View.VISIBLE);
        } else{
            this.checkBox.setVisibility(View.GONE);
        }
    }
}
