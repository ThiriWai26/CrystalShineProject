package com.chann.crystalshineproject.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.adapter.ShopListAdapter;
import com.chann.crystalshineproject.data.ShopList;
import com.chann.crystalshineproject.data.ShopListResponse;
import com.chann.crystalshineproject.data.ShopSearchResponse;
import com.chann.crystalshineproject.data.Token;
import com.chann.crystalshineproject.holder.ShopListHolder;
import com.chann.crystalshineproject.service.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopListActivity extends AppCompatActivity implements  ShopListHolder.OnShopListItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ShopListAdapter adapter;
    private ShopListAdapter searchAdapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private androidx.appcompat.widget.SearchView searchView;
    private FloatingActionButton floatingActionButton;
    private String token = null;
    private int townshipId = -1;
    private int projectId = -1;
    List<ShopList> shopListList = new ArrayList<>();
    List<ShopList> shopLists = new ArrayList<>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_list);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.sv);
        floatingActionButton = findViewById(R.id.fab);

        adapter = new ShopListAdapter(this);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        searchAdapter = new ShopListAdapter(this);

        Bundle bundle = getIntent().getExtras();
        townshipId = bundle.getInt("townshipId");
        Log.e("townshipId",String.valueOf(townshipId));

        projectId = bundle.getInt("projectId");
        Log.e("projectId",String.valueOf(projectId));

        Bundle b = getIntent().getExtras();
        token = b.getString("Token");

        initActivity();
        initApi();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("floatingbutton","click");

                Intent intent = new Intent(getApplicationContext(), AddShopListActivity.class);
                intent.putExtra("townshipId", townshipId );
                intent.putExtra("token", token);
                startActivity(intent);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    recyclerView.setAdapter(adapter);
                }else{
                    Log.e("token", token);
                    Log.e("newtext", newText);
                    RetrofitService.getApiEnd().shopSearch(token,newText).enqueue(new Callback<ShopSearchResponse>() {
                        @Override
                        public void onResponse(Call<ShopSearchResponse> call, Response<ShopSearchResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().isSuccess){
                                    recyclerView.setAdapter(searchAdapter);
                                    searchAdapter.addItem(response.body().shopList);
                                    searchAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ShopSearchResponse> call, Throwable t) {

                        }
                    });
                }
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initApi() {
        Log.e("api","success");
         RetrofitService.getApiEnd().shopList(token,townshipId).enqueue(new Callback<ShopListResponse>() {
             @Override
             public void onResponse(Call<ShopListResponse> call, Response<ShopListResponse> response) {
                 if(response.isSuccessful()){
                     if(response.body().isSuccess){
                         Log.e("response.body","success");
                         adapter.addItem(response.body().shopList);
                         adapter.notifyDataSetChanged();
                     }
                     else{
                         Log.e("response.body", "fail");
                     }
                 }
                 else{
                     Toast.makeText(getApplicationContext(), response.body().errorMessage, Toast.LENGTH_LONG);
                 }
             }

             @Override
             public void onFailure(Call<ShopListResponse> call, Throwable t) {
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
    public void onShopClick(int id) {
        Intent intent = new Intent(getApplicationContext(), ShopDetailActivity.class);
        intent.putExtra("Token",token);
        intent.putExtra("shopId", id);
        intent.putExtra("projectId", projectId);
        intent.putExtra("townshipId", townshipId);
        startActivity(intent);
    }


}
