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
import android.view.View;
import android.widget.SearchView;
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
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ShopListHolder.OnShopListItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ShopListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private androidx.appcompat.widget.SearchView searchView;
    private FloatingActionButton floatingActionButton;
    private String token = null;
    private int townshipId = -1;
    List<ShopList> shopListList = new ArrayList<>();

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

        Bundle bundle = getIntent().getExtras();
        townshipId = bundle.getInt("townshipId");
        Log.e("townshipId",String.valueOf(townshipId));

        Bundle b = getIntent().getExtras();
        token = b.getString("Token");

        initActivity();
        initApi();

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("onquerysubmit", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("onquerysubmit", newText);

                if(newText.isEmpty()) {
                }
                else {
                    RetrofitService.getApiEnd().shopSearch(token,newText).enqueue(new Callback<ShopSearchResponse>() {
                        @Override
                        public void onResponse(Call<ShopSearchResponse> call, Response<ShopSearchResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().isSuccess){
                                    Log.e("search","success");
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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("floatingbutton","click");

                Intent intent = new Intent(getApplicationContext(), AddShopListActivity.class);
                intent.putExtra("townshipId", townshipId );
                startActivity(intent);

            }
        });

    }

    private void initApi() {
        Log.e("api","success");
         RetrofitService.getApiEnd().shopList(token,townshipId).enqueue(new Callback<ShopListResponse>() {
             @Override
             public void onResponse(Call<ShopListResponse> call, Response<ShopListResponse> response) {
                 if(response.isSuccessful()){
                     if(response.body().isSuccess){
                         Log.e("response.body","success");
                         adapter.addItem(response.body().shopList);
                         Log.e("size", String.valueOf(shopListList.size()));
                         adapter.notifyDataSetChanged();
                     }
                     else{
                         Log.e("response.body", "fail");
                     }
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
    public void onShopClick(int id) {
        Intent intent = new Intent(getApplicationContext(), ShopDetailActivity.class);
        intent.putExtra("Token",token);
        intent.putExtra("shopId", id);
        startActivity(intent);
    }


}
