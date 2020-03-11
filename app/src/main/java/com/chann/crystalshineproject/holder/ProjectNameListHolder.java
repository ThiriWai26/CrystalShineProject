package com.chann.crystalshineproject.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.api.OnProjectNameItemClickListener;
import com.chann.crystalshineproject.data.ProjectNameList;

public class ProjectNameListHolder extends RecyclerView.ViewHolder {

    private OnProjectNameItemClickListener listener;
    private TextView tvName, tvType;
    private RelativeLayout layout;

    public ProjectNameListHolder(@NonNull View itemView, OnProjectNameItemClickListener listener) {
        super(itemView);
        this.listener = this.listener;
        init(itemView);
    }

    private void init(View itemView) {

        tvName = itemView.findViewById(R.id.tvName);
        tvType = itemView.findViewById(R.id.tvType);
        layout = itemView.findViewById(R.id.projectNameList);

    }

    public static ProjectNameListHolder create(LayoutInflater inflater, ViewGroup parent, OnProjectNameItemClickListener listener) {
        View view = inflater.inflate(R.layout.layout_projectnamelist_item , parent , false);
        return new ProjectNameListHolder( view , listener);
    }

    public void bindData(ProjectNameList projectNameList) {

    }
}