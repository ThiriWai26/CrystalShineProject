package com.chann.crystalshineproject.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.adapter.SpinnerCategoryAdapter;
import com.chann.crystalshineproject.data.EditShopList;
import com.chann.crystalshineproject.data.ShopCategoriesResponse;
import com.chann.crystalshineproject.data.ShopCategory;
import com.chann.crystalshineproject.data.ShopStoreResponse;
import com.chann.crystalshineproject.data.Token;
import com.chann.crystalshineproject.data.Towns;
import com.chann.crystalshineproject.data.TownsResponse;
import com.chann.crystalshineproject.data.Township;
import com.chann.crystalshineproject.data.TownshipsResponse;
import com.chann.crystalshineproject.service.RetrofitService;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditShopListActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private EditText edtshopName, edtaddress;
    private TextView tvphoto;
    private Spinner spinnerCategory, spinnerTownship, spinnerTown, spinnerRating, spinnerGrade;
    private RatingBar ratingBar;
    private CompositeDisposable disposable;

    private String imagePath = "";
    private String token = null;
    private String name = null;
    private String address = null;
    private String category;
    private String township;
    private String rating;
    private String grade;

    private int categoryId = -1;
    private int townshipId = -1;
    List<String> rate = new ArrayList<>();
    List<String> grades = new ArrayList<>();
    List<String> categories = new ArrayList<>();
    List<String> towns = new ArrayList<>();
    List<String> townships = new ArrayList<>();
    private List<ShopCategory> shopCategoryList = new ArrayList<>();
    private List<Towns> townsList = new ArrayList<>();
    private List<Township> townshipList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop_list);

        initActivity();
    }

    private void initActivity() {

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
//        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
    }

    private void init() {

        edtshopName = findViewById(R.id.edtshopName);
        edtaddress = findViewById(R.id.edtAddress);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerTownship = findViewById(R.id.spinnerTownship);
        spinnerTown  = findViewById(R.id.spinnertown);
        spinnerRating = findViewById(R.id.spinnerrating);
        spinnerGrade = findViewById(R.id.spinnergrade);

        ratingBar = findViewById(R.id.rating);

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("Token");

        townshipId = bundle.getInt("townshipId");
        Log.e("townshipId",String.valueOf(townshipId));

        name = bundle.getString("name");
        Log.e("name", name);

        address = bundle.getString("address");
        Log.e("address", address);

        edtshopName.setText(getIntent().getStringExtra("name"));
        edtaddress.setText(getIntent().getStringExtra("address"));

        loadSpinnerCategory();
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String s =  spinnerCategory.getItemAtPosition(spinnerCategory.getSelectedItemPosition()).toString();
                categoryId = shopCategoryList.get(position).id;
                Log.e("categoryId", String.valueOf(categoryId));
                Log.e("name", s);

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        loadSpinnerTown();
        spinnerTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = spinnerTown.getItemAtPosition(spinnerTown.getSelectedItemPosition()).toString();
                int townId = townsList.get(i).id;
                Log.e("name", spinnerTown.getSelectedItem().toString());
                Log.e("id", String.valueOf(townId));
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                Log.e("token", token);
                Log.e("townId", String.valueOf(townId));

                RetrofitService.getApiEnd().townships(token,townId).enqueue(new Callback<TownshipsResponse>() {
                    @Override
                    public void onResponse(Call<TownshipsResponse> call, Response<TownshipsResponse> response) {
                        if(response.isSuccessful()){
                            if (response.body().isSuccess) {
                                Log.e("response.body","success");
                                List<Township> township = response.body().township;
                                List<String> townshipLists = new ArrayList<>();

                                for(int i=0; i<township.size(); i++){
                                    String name = township.get(i).name;
                                    townshipLists.add(name);
                                    Log.e("township_size", String.valueOf(response.body().township.size()));
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(EditShopListActivity.this, R.layout.spinner_item, townshipLists);
                                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                spinnerTownship.setAdapter(adapter);
                            }
                            else{
                                Log.e("response.body","fail");
                            }
                        }
                        else{
                            Log.e("response","fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<TownshipsResponse> call, Throwable t) {
                        Log.e("failure", t.toString());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        grades.add("A");
        grades.add("B");
        grades.add("C");
        grades.add("D");
        grades.add("E");
        ArrayAdapter gradesAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, grades);
        gradesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(gradesAdapter);
        spinnerGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = spinnerGrade.getItemAtPosition(spinnerGrade.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rate.add("1");
        rate.add("2");
        rate.add("3");
        rate.add("4");
        rate.add("5");
        ArrayAdapter ratesAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, rate);
        ratesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerRating.setAdapter(ratesAdapter);
        spinnerRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = spinnerRating.getItemAtPosition(spinnerRating.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private void loadSpinnerCategory() {
        RetrofitService.getApiEnd().shopCategory(token).enqueue(new Callback<ShopCategoriesResponse>() {
            @Override
            public void onResponse(Call<ShopCategoriesResponse> call, Response<ShopCategoriesResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");
                        shopCategoryList = response.body().shopCategory;
                        List<ShopCategory> shopCategory = response.body().shopCategory;
                        List<String> shopCategories = new ArrayList<>();
                        for(int i=0; i<shopCategory.size(); i++)
                        {
                            String name = shopCategory.get(i).name;
                            shopCategories.add(name);
                            Log.e("name", name);
                            Log.e("category_size", String.valueOf(response.body().shopCategory.size()));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, shopCategories);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinnerCategory.setAdapter(adapter);
                    }
                    else{
                        Log.e("response.body","fail");
                    }
                }
                else{
                    Log.e("response","fail");
                }
            }

            @Override
            public void onFailure(Call<ShopCategoriesResponse> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });

    }

    private void loadSpinnerTown() {
        RetrofitService.getApiEnd().towns(token).enqueue(new Callback<TownsResponse>() {
            @Override
            public void onResponse(Call<TownsResponse> call, Response<TownsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess) {
                        Log.e("response.body", "success");
                        townsList = response.body().towns;
                        List<Towns> town = response.body().towns;
                        List<String> townlist = new ArrayList<>();

                        for (int i = 0; i < town.size(); i++) {
                            String name = town.get(i).name;
                            townlist.add(name);
                            Log.e("town_size", String.valueOf(response.body().towns.size()));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditShopListActivity.this, R.layout.spinner_item, townlist);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinnerTown.setAdapter(adapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<TownsResponse> call, Throwable t) {

            }
        });
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && data != null) {
//
//            Uri uri = data.getData();
//            imagePath = getImageFilePath(data.getData());
////            tvphoto.setText(imagePath);
//        }
//    }
//
//    private String getImageFilePath(Uri data) {
//
//        File file = new File(data.getPath());
//        String[] filePath = file.getPath().split(":");
//        String image_id = filePath[filePath.length - 1];
//        Cursor cursor = getApplicationContext().getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            cursor.close();
//            Log.e("imagePath",imagePath);
//
//            String imageName = file.getName();
//            Log.e("imageName", imageName);
//            Log.e("imageName", file.getName());
//
//            String filename = imagePath.substring(imagePath.lastIndexOf("/"));
//            tvphoto.setText(filename);
//
//            return  imagePath;
//        }
//
//        return data.getPath();
//    }
//
//    public void onPhoto(View view) {
//        Log.e("photoclick","ok");
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    2);
//        } else {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
//
//        }
//
//    }

    public void onClickShopSave(View view) {

        name = edtshopName.getText().toString();
        address = edtaddress.getText().toString();
        category =spinnerCategory.getSelectedItem().toString();
        township = spinnerTownship.getSelectedItem().toString();
        rating = spinnerRating.getSelectedItem().toString();
        grade = spinnerGrade.getSelectedItem().toString();

        Log.e("name", name);
        Log.e("address", address);
        Log.e("category", category);
        Log.e("township", township);
        Log.e("rating", String.valueOf(rating));
        Log.e("grade", grade);
        Log.e("token", Token.MyToken.getToken());
        Log.e("categoryId", String.valueOf(categoryId));
        Log.e("townshipId", String.valueOf(townshipId));

        if (edtshopName.getText().toString().isEmpty())
            edtshopName.setError("Enter Shop Name");
        else name = edtshopName.getText().toString();

        if (edtaddress.getText().toString().isEmpty())
            edtaddress.setError("Enter Address");
        else address = edtaddress.getText().toString();

        RetrofitService.getApiEnd().shopStoreUpdate(token,categoryId,townshipId,rate,grade,name,address).enqueue(new Callback<ShopStoreResponse>() {
            @Override
            public void onResponse(Call<ShopStoreResponse> call, Response<ShopStoreResponse> response) {
                Log.e("api","ok");
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");
                        Toast.makeText(EditShopListActivity.this, "Change Success", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), ProjectNameListActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Log.e("response.body","fail");
                        Toast.makeText(getApplicationContext(),"Update fail",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Log.e("failure","fail");
                }

            }

            @Override
            public void onFailure(Call<ShopStoreResponse> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });


    }


}
