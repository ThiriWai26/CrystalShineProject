package com.chann.crystalshineproject.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chann.crystalshineproject.data.Towns;
import com.chann.crystalshineproject.holder.TownHolder;

import java.util.ArrayList;
import java.util.List;

public class TownAdapter extends RecyclerView.Adapter<TownHolder> {

    private TownHolder.OnTownItemClickListener listener;
    private List<Towns> townsList;

    public TownAdapter(TownHolder.OnTownItemClickListener listener){

        this.listener = listener;
        townsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TownHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return TownHolder.create(inflater , parent , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TownHolder holder, int position) {
        holder.bindData(townsList.get(position));
    }

    @Override
    public int getItemCount() {
        return townsList.size();
    }

    public void addItem(List<Towns> towns) {
        if (townsList.isEmpty()) {
            this.townsList = towns;
        } else
            this.townsList.addAll(towns);
        notifyDataSetChanged();
    }
}
