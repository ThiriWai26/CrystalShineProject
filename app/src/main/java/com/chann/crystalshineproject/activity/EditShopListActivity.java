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
import com.chann.crystalshineproject.databinding.ActivityEditShopListBinding;
import com.chann.crystalshineproject.databinding.ActivityShopListBinding;
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

public class EditShopListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private EditText edtshopName, edtaddress;
    private TextView tvphoto;
    private Spinner spinnerCategory, spinnerTownship;
    private RatingBar ratingBar;
    private ActivityEditShopListBinding binding;
    private CompositeDisposable disposable;

    private String imagePath = "";
    ArrayAdapter<String> adapter;
    private String token = null;
    List<ShopCategory> shopCategory = new ArrayList<>();
    List<String> townships = new ArrayList<>();
    private SpinnerCategoryAdapter spinnerCategoryAdapter;

    private int categoryId = -1;
    private int townshipId = -1;
    private String photo;

    private int rate = -1;
    private int grade  = -1;
    private char name =' ';
    private String address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop_list);

//        disposable = new CompositeDisposable();
//
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_shop_list);

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
        tvphoto = findViewById(R.id.edtphoto);
        edtaddress = findViewById(R.id.edtAddress);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerTownship = findViewById(R.id.spinnerTownship);
        ratingBar = findViewById(R.id.rating);

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("Token");

        townshipId = bundle.getInt("townshipId");
        Log.e("townshipId",String.valueOf(townshipId));

        categoryId = bundle.getInt("categoryId");
        Log.e("categoryId",String.valueOf(categoryId));

        photo = bundle.getString("photo");
        Log.e("photo", photo);

        rate = bundle.getInt("rate");
        Log.e("rate", String.valueOf(rate));

        grade = bundle.getInt("grade");
        Log.e("grade", String.valueOf(grade));

//        spinnerCategoryAdapter = new SpinnerCategoryAdapter(this, R.layout.spinner_item, shopCategory.name);

//        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.e("api","success");
//                RetrofitService.getApiEnd().shopCategory(token).enqueue(new Callback<ShopCategoriesResponse>() {
//                    @Override
//                    public void onResponse(Call<ShopCategoriesResponse> call, Response<ShopCategoriesResponse> response) {
//                        if(response.isSuccessful()){
//                            if(response.body().isSuccess){
//                                Log.e("response.body","success");
//
////                                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item);
////                                spinnerCategory.setAdapter(adapter);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ShopCategoriesResponse> call, Throwable t) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        String[] data = {"Eos", "Mollita", "Offica", "Commoidi", "Cum est"};
        String[] data1 = {"East Murray", "Palmaburgh", "South Breannamouth", "Mayaville", "East Nicotown"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, data);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,R.layout.spinner_item, data1);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerTownship.setAdapter(adapter1);

        ratingBar.setRating(rate);

//        adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item,townships);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinnerCategory.setAdapter(adapter);
//        spinnerTownship.setAdapter(adapter);

        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "Gallery", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "Slideshow", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "Tools", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);// 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {

            Uri uri = data.getData();
            imagePath = getImageFilePath(data.getData());
//            tvphoto.setText(imagePath);
        }
    }

    private String getImageFilePath(Uri data) {

        File file = new File(data.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getApplicationContext().getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            Log.e("imagePath",imagePath);

            String imageName = file.getName();
            Log.e("imageName", imageName);
            Log.e("imageName", file.getName());

            String filename = imagePath.substring(imagePath.lastIndexOf("/"));
            tvphoto.setText(filename);

            return  imagePath;
        }

        return data.getPath();
    }

    public void onPhoto(View view) {
        Log.e("photoclick","ok");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
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

    public void onClickSave(View view) {

        Log.e("onclicksave", "ok");
        MultipartBody.Part photo = null;
        File file = new File(imagePath);
        Log.e("originalfilesize",String.valueOf(file.length()/1024));
        File compressFile = null;

        try {
            compressFile = new Compressor(getApplicationContext()).compressToFile(file);
            Log.e("compressfilesize",String.valueOf(compressFile.length()/1024));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("fileimagePath",imagePath);
        Log.e("shop name", String.valueOf(edtshopName));
        Log.e("address", String.valueOf(edtaddress));
        Log.e("token", String.valueOf(token));

        RequestBody imageBody = RequestBody.create( MediaType.parse("multipart/form-data"), file);
        photo = MultipartBody.Part.createFormData("image",file.getName(),imageBody);

        Log.e("file name",file.getName());

        Log.e("api","start");

        RetrofitService.getApiEnd().shopStoreUpdate(token,categoryId,townshipId,photo,rate,grade,name,address).enqueue(new Callback<ShopStoreResponse>() {
            @Override
            public void onResponse(Call<ShopStoreResponse> call, Response<ShopStoreResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");
                    }
                }
            }

            @Override
            public void onFailure(Call<ShopStoreResponse> call, Throwable t) {

            }
        });


    }
}
