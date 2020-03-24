package com.chann.crystalshineproject.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.data.Township;

public class TownshipListHolder extends RecyclerView.ViewHolder {

    private OnTownshipItemClickListener listener;
    private TextView tvName;
    private RelativeLayout layout;

    public TownshipListHolder(View view, TownshipListHolder.OnTownshipItemClickListener listener) {
        super(view);
        this.listener = listener;
        init(itemView);

    }

    private void init(View itemView) {
        tvName = itemView.findViewById(R.id.tvName);
        layout = itemView.findViewById(R.id.relative);

    }

    public static TownshipListHolder create(LayoutInflater inflater, ViewGroup parent, TownshipListHolder.OnTownshipItemClickListener listener) {
        View view = inflater.inflate(R.layout.layout_townshiplist_item , parent , false);
        return new TownshipListHolder( view , listener);
    }

    public void bindData(Township township) {
        tvName.setText(township.name);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTownshipClick(township.id);
            }
        });
    }

    public interface OnTownshipItemClickListener {
        void onTownshipClick(int id);
    }
}
