package com.chann.crystalshineproject.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.data.EditShopList;

import java.io.File;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditShopListFragment extends Fragment {

    private CompositeDisposable compositeDisposable;
    private static EditShopList editShopList;
    private EditText edtshopName, edtphoto, edtaddress;
    private Spinner spinnerCategory, spinnerTownship;
    private String imagePath = "";

    public EditShopListFragment() {
        // Required empty public constructor
    }
    public static Intent getInstance(Context context, EditShopList edtShopList) {

        editShopList = edtShopList;
        Intent intent = new Intent(context, EditShopListFragment.class);
        return intent;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_shop_list, container, false);

        initFragment(view);
        return view;
    }

    private void initFragment(View view) {

        compositeDisposable = new CompositeDisposable();
        edtshopName = view.findViewById(R.id.edtshopName);
        edtphoto = view.findViewById(R.id.edtphoto);
        edtaddress = view.findViewById(R.id.edtAddress);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerTownship = view.findViewById(R.id.spinnerTownship);


    }

    public void onClickProfile(View view){

            if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        2);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

            }

        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {

            Uri uri = data.getData();

//            binding.profile.setImageURI(uri);

            imagePath = getImageFilePath(data.getData());
//            binding.tvSave.setVisibility(View.VISIBLE);

        }
    }

    private String getImageFilePath(Uri data) {
        File file = new File(data.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContext().getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            Log.e("imagePath",imagePath);
            return  imagePath;
        }

        return null;
    }

}
