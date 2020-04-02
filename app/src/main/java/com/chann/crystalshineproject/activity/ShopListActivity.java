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

public class ShopListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ShopListHolder.OnShopListItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ShopListAdapter adapter;
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

        Bundle bundle = getIntent().getExtras();
        townshipId = bundle.getInt("townshipId");
        Log.e("townshipId",String.valueOf(townshipId));

        projectId = bundle.getInt("projectId");
        Log.e("projectId",String.valueOf(projectId));

        Bundle b = getIntent().getExtras();
        token = b.getString("Token");

        initActivity();
        initApi();

//        serchViewFilter();
//        searchViewModify();

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

        if (bundle != null) {
            char name = bundle.getChar("name");
            getSearchItem(token,name);

        }
    }

    private void getSearchItem(String token, char name) {
        RetrofitService.getApiEnd().shopSearch(token,name).enqueue(new Callback<ShopSearchResponse>() {
            @Override
            public void onResponse(Call<ShopSearchResponse> call, Response<ShopSearchResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");
                        adapter.addItem(response.body().shopList);
                    }else {
                        Log.e("response.body","fail");
                    }
                }else{
                    Log.e("response","fail");
                }
            }

            @Override
            public void onFailure(Call<ShopSearchResponse> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });
    }


    private void serchViewFilter() {

//        RetrofitService.getApiEnd().shopSearch(token,name).enqueue(new Callback<ShopSearchResponse>() {
//            @Override
//            public void onResponse(Call<ShopSearchResponse> call, Response<ShopSearchResponse> response) {
//                if(response.isSuccessful()){
//                    if(response.body().isSuccess){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ShopSearchResponse> call, Throwable t) {
//
//            }
//        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.toLowerCase(Locale.getDefault());
                if(query.length() != 0){
                    shopLists.clear();
                    for (ShopList shopList : shopListList) {
                        if (shopList.name.toLowerCase(Locale.getDefault()).contains(query)) {

                            shopLists.add(shopList);
                        }
                    }
                    adapter.addItem(shopLists);
                } else {
                    adapter.addItem(shopListList);
                }
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase(Locale.getDefault());
                if(newText.length() != 0){
                    shopLists.clear();
                    for (ShopList shopList : shopListList) {
                        if (shopList.name.toLowerCase(Locale.getDefault()).contains(newText)) {

                            shopLists.add(shopList);
                        }
                    }
                    adapter.addItem(shopLists);
                } else {
                    adapter.addItem(shopListList);
                }

                Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });
//
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void searchViewModify(){

        androidx.appcompat.widget.SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setTextColor(Color.BLACK);
        searchAutoComplete.setHint("Search Shop");
        searchAutoComplete.setHintTextColor(Color.BLACK);
        searchAutoComplete.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

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
        intent.putExtra("projectId", projectId);
        intent.putExtra("townshipId", townshipId);
        startActivity(intent);
    }


}
