package com.chann.crystalshineproject.holder;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.api.OnProjectNameItemClickListener;
import com.chann.crystalshineproject.data.ShopList;

import java.util.List;

public class ShopListHolder extends RecyclerView.ViewHolder {

    private OnShopListItemClickListener listener;
    private TextView tvName;
    private RelativeLayout layout;

    public ShopListHolder(@NonNull View itemView, OnShopListItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        init(itemView);
    }

    private void init(View itemView) {

        tvName = itemView.findViewById(R.id.tvName);
        layout = itemView.findViewById(R.id.layout);

    }

    public static ShopListHolder create(LayoutInflater inflater, ViewGroup parent, OnShopListItemClickListener listener) {
        View view = inflater.inflate(R.layout.layout_shoplist_item , parent , false);
        return new ShopListHolder( view , listener);
    }

    public void bindData(ShopList shopList) {

        tvName.setText(shopList.name);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onShopClick(shopList.id);
                Intent intent = new Intent();
                intent.putExtra("name", String.valueOf(tvName));
                Log.e("Name", String.valueOf(tvName));
            }
        });

    }

    public interface OnShopListItemClickListener {
        void onShopClick(int id);
    }
}
