package com.chann.crystalshineproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chann.crystalshineproject.data.ShopCategory;

import java.util.List;

public class SpinnerCategoryAdapter extends ArrayAdapter<String> {

    private LayoutInflater layoutInflater;
    private Context mcontext;
    private List<ShopCategory> shopCategoryList;
    private int mResource;

    public SpinnerCategoryAdapter(Context context, int resource, List<ShopCategory> shopCategorie) {
        super(context, resource);

        mcontext = context;
        layoutInflater = LayoutInflater.from(context);
        mResource = resource;
        shopCategoryList = shopCategorie;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {

        ShopCategory shopCategory = shopCategoryList.get(position);
        Log.e("shopCategory", String.valueOf(shopCategory));

        shopCategoryList.get(position);

        return convertView;
    }
}
