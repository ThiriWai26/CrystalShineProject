package com.chann.crystalshineproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.data.ShopCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private List<ShopCategory> shopCategory = new ArrayList<>();
    private LayoutInflater layoutInflater = null;
    private Context context;

    @SuppressLint("NewApi")
    public CategoryAdapter(Activity context, List<ShopCategory> shopCategory){
        this.shopCategory = shopCategory;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shopCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

//        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder();
//        View view = convertView;
//        if (view == null)
//        {
//            view = layoutInflater.inflate(R.layout.spinner_item, null);
//            viewHolder.textView_vehicleName = (TextView) view.findViewById(R.id.textView_vehicleName);
//            view.setTag(viewHolder);
//        }
//        else
//        {
//            viewHolder = (ViewHolder) view.getTag();
//        }

        ShopCategory vehicles = shopCategory.get(position);
        String name = vehicles.name;
        int id = vehicles.id;

        Log.e("name", name);
        Log.e("id", String.valueOf(id));
        return view;
    }
}
