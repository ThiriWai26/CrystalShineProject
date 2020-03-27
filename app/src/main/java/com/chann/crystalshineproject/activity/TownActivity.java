package com.chann.crystalshineproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.adapter.TownAdapter;
import com.chann.crystalshineproject.data.Token;
import com.chann.crystalshineproject.data.Towns;
import com.chann.crystalshineproject.data.TownsResponse;
import com.chann.crystalshineproject.holder.TownHolder;
import com.chann.crystalshineproject.service.RetrofitService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TownActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TownHolder.OnTownItemClickListener {

    private RecyclerView recyclerView;
    private TownAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private String token = null;
    private int projectId = -1;
    List<Towns> townList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);

        init();
    }

    private void init() {

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new TownAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        token = Token.MyToken.getToken();

//        initActivity();
        initApi();
    }

    private void initApi() {
        RetrofitService.getApiEnd().towns(token).enqueue(new Callback<TownsResponse>() {
            @Override
            public void onResponse(Call<TownsResponse> call, Response<TownsResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");
                        adapter.addItem(response.body().towns);
                        Log.e("size", String.valueOf(townList.size()));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<TownsResponse> call, Throwable t) {

            }
        });
    }

    private void initActivity() {

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    @Override
    public void onTownClick(int id) {
        Intent intent = new Intent(getApplicationContext(), TownshipListActivity.class);
        intent.putExtra("Token", token);
        Log.e("projectId",String.valueOf(id));
        startActivity(intent);
    }
}
