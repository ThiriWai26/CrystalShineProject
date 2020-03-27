package com.chann.crystalshineproject.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.activity.TownActivity;
import com.chann.crystalshineproject.data.Towns;

public class TownHolder extends RecyclerView.ViewHolder {

    private OnTownItemClickListener listener;
    private TextView tvName;
    private RelativeLayout layout;

    public TownHolder(@NonNull View itemView, TownHolder.OnTownItemClickListener listener) {
        super(itemView);
        this.listener = this.listener;
        init(itemView);
    }
    

    private void init(View itemView) {
        tvName = itemView.findViewById(R.id.tvName);
        layout = itemView.findViewById(R.id.relative);
    }


    public static TownHolder create(LayoutInflater inflater, ViewGroup parent, TownHolder.OnTownItemClickListener listener) {
        View view = inflater.inflate(R.layout.layout_town_item , parent , false);
        return new TownHolder( view , listener);
    }

    public void bindData(Towns towns) {
        tvName.setText(towns.name);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTownClick(towns.id);
            }
        });
    }

    public interface OnTownItemClickListener {
        void onTownClick(int id);
    }
}
