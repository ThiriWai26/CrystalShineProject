package com.chann.crystalshineproject.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.crystalshineproject.data.Township;
import com.chann.crystalshineproject.holder.TownshipListHolder;

import java.util.List;

public class TownshipListAdapter extends RecyclerView.Adapter<TownshipListHolder> {

    private TownshipListHolder.OnTownshipItemClickListener listener;
    private List<Township> townshipList;

    public TownshipListAdapter(TownshipListHolder.OnTownshipItemClickListener listener){

        this.listener = listener;
    }


    @NonNull
    @Override
    public TownshipListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return TownshipListHolder.create(inflater , parent , listener);    }

    @Override
    public void onBindViewHolder(@NonNull TownshipListHolder holder, int position) {
        holder.bindData(townshipList.get(position));
    }

    @Override
    public int getItemCount() {
        return townshipList.size();
    }

    public void addItem(Township township) {
//        if(townshipList.isEmpty()){
//            this.townshipList = township;
//        }else
//            this.shopLists.add(shopList);
        notifyDataSetChanged();
    }
}
