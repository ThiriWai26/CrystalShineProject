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
import com.chann.crystalshineproject.adapter.TownshipListAdapter;
import com.chann.crystalshineproject.data.ShopListResponse;
import com.chann.crystalshineproject.data.Token;
import com.chann.crystalshineproject.data.Township;
import com.chann.crystalshineproject.data.TownshipResponse;
import com.chann.crystalshineproject.holder.TownshipListHolder;
import com.chann.crystalshineproject.service.RetrofitService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TownshipListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TownshipListHolder.OnTownshipItemClickListener {

    private RecyclerView recyclerView;
    private TownshipListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private String token = null;
    private int projectId = -1;
    List<Township> townshipList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_township_list);

        init();
    }

    private void init() {

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new TownshipListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        token = Token.MyToken.getToken();

        Bundle bundle = getIntent().getExtras();
        projectId = bundle.getInt("projectId");
        Log.e("buildingId",String.valueOf(projectId));

        initActivity();
        initApi();

    }

    private void initApi() {
        RetrofitService.getApiEnd().townshopList(token,projectId).enqueue(new Callback<TownshipResponse>() {
            @Override
            public void onResponse(Call<TownshipResponse> call, Response<TownshipResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");
                        adapter.addItem(response.body().township);
                        Log.e("size", String.valueOf(townshipList.size()));
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Log.e("response.body","fail");
                    }
                }
            }

            @Override
            public void onFailure(Call<TownshipResponse> call, Throwable t) {
                Log.e("failure", t.toString());
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
    public void onTownshipClick(int id) {
        Intent intent = new Intent(getApplicationContext(), ShopListActivity.class);
        intent.putExtra("townshipId", id);
        Log.e("townshipId",String.valueOf(id));
        startActivity(intent);
    }
}
