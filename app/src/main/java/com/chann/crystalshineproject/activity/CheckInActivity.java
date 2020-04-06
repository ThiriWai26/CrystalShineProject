package com.chann.crystalshineproject.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.data.ShopDetail;
import com.chann.crystalshineproject.data.ShopReportResponse;
import com.chann.crystalshineproject.data.Token;
import com.chann.crystalshineproject.service.RetrofitService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInActivity extends AppCompatActivity  {

    private DrawerLayout mDrawerLayout;

    private ImageView imgcamera;
    private TextView tvtitle;
    private Button btnsubmit;
    private String token = null;
    private String imagePath = "";
    private int shopId = -1;
    private int projectId = -1;
    private int townshipId = -1;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        initActivity();

    }

    private void initActivity() {

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        init();
    }

    @SuppressLint("NewApi")
    private void init() {

        imgcamera = findViewById(R.id.imgcamera);
        btnsubmit = findViewById(R.id.btnsubmit);
        tvtitle = findViewById(R.id.tvTitle);

        Bundle bundle = getIntent().getExtras();

        token = bundle.getString("Token");

        shopId = bundle.getInt("shopId");
        Log.e("shopId",String.valueOf(shopId));

        projectId = bundle.getInt("projectId");
        Log.e("projectId", String.valueOf(projectId));

        name = bundle.getString("name");
        Log.e("name", name);

        address = bundle.getString("address");
        Log.e("address",address);

        townshipId = bundle.getInt("townshipId");
        Log.e("townshipId", String.valueOf(townshipId));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@io.reactivex.annotations.NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    String s = location.getLatitude() + "," + location.getLongitude();
                                    Toast.makeText( getApplicationContext(),s,Toast.LENGTH_SHORT ).show();
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                    Log.e("Location", location.getLatitude() + "," + location.getLongitude());
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }



    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }


    public void onClickCamera(View view) {

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {

            Uri uri = data.getData();

            imgcamera.setImageURI(uri);

            imagePath = getImageFilePath(data.getData());

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

            return  imagePath;
        }

        return data.getPath();
    }


    public void onSubmit(View view) {

        Log.e("onclicksave", "ok");
        RequestBody token = RequestBody.create( MediaType.parse("multipart/form-data"), Token.MyToken.getToken());
        Log.e("Token", String.valueOf(token));
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

        Log.e("file name",file.getName());
        Log.e("fileimagePath",imagePath);
        RequestBody imageBody = RequestBody.create( MediaType.parse("multipart/form-data"), file);
        photo = MultipartBody.Part.createFormData("photo",file.getName(),imageBody);

        Log.e("token", Token.MyToken.getToken());
        Log.e("shopId", String.valueOf(shopId));
        Log.e("projectId", String.valueOf(projectId));
        Log.e("photo", file.getName());
        Log.e("latitude", String.valueOf(latitude));
        Log.e("logitude", String.valueOf(longitude));

        Log.e("api","bind");
        RetrofitService.getApiEnd().shopReport(token,shopId,projectId,photo,latitude,longitude).enqueue(new Callback<ShopReportResponse>() {
            @Override
            public void onResponse(Call<ShopReportResponse> call, Response<ShopReportResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body", "success");
                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();

//                        Intent intent = new Intent(getApplicationContext(), ProjectNameListActivity.class);
//                        startActivity(intent);
                    }else {
                        Log.e("response.body","fail");
                        Toast.makeText(getApplicationContext(), (CharSequence) response.body().errorMessage, Toast.LENGTH_LONG).show();
                    }
                }else{
                    Log.e("response","failure");
                }
            }

            @Override
            public void onFailure(Call<ShopReportResponse> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });


    }

    public void onDetail(View view) {
        Intent intent = new Intent(getApplicationContext(), EditShopListActivity.class);
        intent.putExtra("Token",token);
        intent.putExtra("shopId", shopId);
        intent.putExtra("projectId", projectId);
        intent.putExtra("name", String.valueOf(name));
        intent.putExtra("address", String.valueOf(address));
        intent.putExtra("townshipId", townshipId);
        Log.e("TOKEN", token);

        startActivity(intent);
    }
}
