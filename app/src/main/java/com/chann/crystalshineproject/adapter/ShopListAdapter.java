package com.chann.crystalshineproject.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.crystalshineproject.data.ShopList;
import com.chann.crystalshineproject.holder.ShopListHolder;

import java.util.ArrayList;
import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListHolder> {

    private List<ShopList> shopLists;
    private ShopListHolder.OnShopListItemClickListener listener;

    public ShopListAdapter(ShopListHolder.OnShopListItemClickListener listener){

        this.listener = listener;
        shopLists = new ArrayList<>();
    }


    @NonNull
    @Override
    public ShopListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return ShopListHolder.create(inflater , parent , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListHolder holder, int position) {
        holder.bindData(shopLists.get(position));
    }

    @Override
    public int getItemCount() {
        return shopLists.size();
    }

    public void addItem(List<ShopList> shopList) {

        if(shopLists.isEmpty()){
            this.shopLists=shopList;
        }else{
            this.shopLists.addAll(shopList);
        }
        notifyDataSetChanged();
    }
}
