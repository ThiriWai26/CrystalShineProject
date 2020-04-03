package com.chann.crystalshineproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chann.crystalshineproject.data.ShopCategory;

import java.util.List;

public class SpinnerCategoryAdapter{

    private LayoutInflater layoutInflater;
    private Context context;
    private List<ShopCategory> shopCategories;

//    public SpinnerCategoryAdapter(Context context, int id, List<String> shopCategories) {
//        super(context, id, shopCategories);
//
//        layoutInflater = LayoutInflater.from(context);
//        this.context = context;
//        this.shopCategories = shopCategories;
//    }


//    @Override
//    public int getCount(){
//        return shopCategories.size();
//    }

//    @Override
//    public ShopCategory getItem(int position){
//        return shopCategories.get(position);
//    }
//
//    @Override
//    public long getItemId(int position){
//        return position;
//    }
//
//    @Override
//    public View getDropDownView(int position, @Nullable View convertView,
//                                @NonNull ViewGroup parent) {
//        return createItemView(position, convertView, parent);
//    }

//    @Override
//    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        TextView label = (TextView) super.getView(position, convertView, parent);
//        label.setTextColor(Color.BLACK);
//        // Then you can get the current item using the values array (Users array) and the current position
//        // You can NOW reference each method you has created in your bean object (User class)
////        label.setText(shopCategories.get(position).id);
//
////        Log.e("id", String.valueOf(shopCategories.get(position).id));
//        return convertView;
//    }

    private View createItemView(int position, View convertView, ViewGroup parent) {

        return convertView;
    }
}
