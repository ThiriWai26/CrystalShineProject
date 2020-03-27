package com.chann.crystalshineproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.data.EditShopList;
import com.chann.crystalshineproject.data.ShopDetail;
import com.chann.crystalshineproject.data.ShopDetailResponse;
import com.chann.crystalshineproject.data.Token;
import com.chann.crystalshineproject.service.RetrofitService;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private TextView categoryid, townshipid, tvname, tvtown, tvtype, tvaddress;
    private RatingBar ratingBar;
    private ImageView photo;
    private Button btneditshop;
    private String token = null;
    private int shopId = -1;

    List<ShopDetail> shopDetail = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        initActivity();
        initApi();
    }

    private void initApi() {

        photo = findViewById(R.id.photo);
        categoryid = findViewById(R.id.tvcategoryId);
        townshipid = findViewById(R.id.tvtownshipId);
        tvname = findViewById(R.id.tvName);
        tvtown = findViewById(R.id.tvTown);
        tvtype = findViewById(R.id.tvType);
        tvaddress = findViewById(R.id.tvAddress);
        ratingBar = findViewById(R.id.rating);
        btneditshop = findViewById(R.id.btneditshop);

        Bundle bundle = getIntent().getExtras();
        shopId = bundle.getInt("shopId");
        Log.e("shopId",String.valueOf(shopId));

        token = bundle.getString("Token");

        RetrofitService.getApiEnd().shopDetail(token,shopId).enqueue(new Callback<ShopDetailResponse>() {
            @Override
            public void onResponse(Call<ShopDetailResponse> call, Response<ShopDetailResponse> response) {
                Log.e("api","success");
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");

                        Picasso.get().load(RetrofitService.BASE_URL + "/api/download_image/" + response.body().shopDetail.photo).into(photo);
                        tvname.setText(response.body().shopDetail.name);
                        tvtown.setText(response.body().shopDetail.townshipName);
                        tvtype.setText(response.body().shopDetail.categoryName);
                        tvaddress.setText(response.body().shopDetail.address);
                        ratingBar.setRating(response.body().shopDetail.rate);

                        Log.e("Id", String.valueOf(response.body().shopDetail.id));
                        Log.e("Name", response.body().shopDetail.name);
                        Log.e("Township", response.body().shopDetail.townshipName);
                        Log.e("Category", response.body().shopDetail.categoryName);
                        Log.e("Address", response.body().shopDetail.address);
                        Log.e("Rating", String.valueOf(response.body().shopDetail.rate));

                        btneditshop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext() , EditShopListActivity.class);
                                intent.putExtra("Token",token);
                                intent.putExtra("categoryId", response.body().shopDetail.categoryId);
                                intent.putExtra("townshipId", response.body().shopDetail.townshipId);
                                intent.putExtra("photo", response.body().shopDetail.photo);
                                intent.putExtra("rate", response.body().shopDetail.rate);
                                intent.putExtra("grade", response.body().shopDetail.grade);
                                intent.putExtra("name", response.body().shopDetail.name);
                                intent.putExtra("address", response.body().shopDetail.address);

                                SharedPreferences pref;// 0 - for private mode
                                SharedPreferences.Editor editor;
                                pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                editor = pref.edit();

                                editor.clear();
                                editor.apply();
                                editor.commit();

                                startActivity(intent);
                            }
                        });

                    }
                    else{
                        Toast.makeText(ShopDetailActivity.this,response.body().errorMessage,Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Log.e("response","fail");
                }
            }

            @Override
            public void onFailure(Call<ShopDetailResponse> call, Throwable t) {
                Log.e("failure",t.toString());
            }
        });
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
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
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

}
