package com.chann.crystalshineproject.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.crystalshineproject.api.OnProjectNameItemClickListener;
import com.chann.crystalshineproject.data.ProjectNameList;
import com.chann.crystalshineproject.holder.ProjectNameListHolder;

import java.util.ArrayList;
import java.util.List;

public class ProjectNameListAdapter extends RecyclerView.Adapter<ProjectNameListHolder> {

    private List<ProjectNameList> projectNameLists;
    private OnProjectNameItemClickListener listener;

    public ProjectNameListAdapter(OnProjectNameItemClickListener listener){

        this.listener = listener;
        projectNameLists = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProjectNameListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return ProjectNameListHolder.create(inflater , parent , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectNameListHolder holder, int position) {
        holder.bindData(projectNameLists.get(position));

    }

    @Override
    public int getItemCount() {
        return projectNameLists.size();
    }
}
