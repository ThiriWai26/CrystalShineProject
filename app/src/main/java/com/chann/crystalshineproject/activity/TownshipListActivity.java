package com.chann.crystalshineproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.adapter.TownAdapter;
import com.chann.crystalshineproject.adapter.TownshipListAdapter;
import com.chann.crystalshineproject.data.ShopListResponse;
import com.chann.crystalshineproject.data.Token;
import com.chann.crystalshineproject.data.Towns;
import com.chann.crystalshineproject.data.TownsResponse;
import com.chann.crystalshineproject.data.Township;
import com.chann.crystalshineproject.data.TownshipResponse;
import com.chann.crystalshineproject.holder.TownHolder;
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

public class TownshipListActivity extends AppCompatActivity implements TownshipListHolder.OnTownshipItemClickListener , TownHolder.OnTownItemClickListener {

    private RecyclerView recyclerView;
    private TownshipListAdapter adapter;
    private RecyclerView townfilterrecyclerview;
    private TownAdapter townAdapter;
    private DrawerLayout mDrawerLayout;
    private String token = null;
    private int projectId = -1;
    List<Township> townshipList = new ArrayList<>();
    List<Towns> town = new ArrayList<>();

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
        Log.e("projectId",String.valueOf(projectId));

        Bundle b = getIntent().getExtras();
        token = b.getString("Token");



        initActivity();
        initApi();
    }

    private void initApi() {
        Log.e("api","ok");
        RetrofitService.getApiEnd().townshopList(token,projectId).enqueue(new Callback<TownshipResponse>() {
            @Override
            public void onResponse(Call<TownshipResponse> call, Response<TownshipResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");
                        townshipList = response.body().township;
                        adapter.addItem(response.body().township);
                        Log.e("size", String.valueOf(townshipList.size()));
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Log.e("response.body.township","fail");
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
    public void onTownshipClick(int id) {
        Intent intent = new Intent(getApplicationContext(), ShopListActivity.class);
        intent.putExtra("townshipId", id);
        intent.putExtra("projectId", id);
        intent.putExtra("Token",token);
        Log.e("townshipId",String.valueOf(id));
        startActivity(intent);
    }

    public void onFilterClick(View view) {

        Log.e("DoctorPhoneNumber","success");

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_township_filter);

//        townfilterrecyclerview = dialog.findViewById(R.id.townfilter);
//        townAdapter = new TownAdapter(this);
//        townfilterrecyclerview.setAdapter(townAdapter);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        townfilterrecyclerview.setLayoutManager(linearLayoutManager);

        dialog.show();

//        RetrofitService.getApiEnd().towns(token).enqueue(new Callback<TownsResponse>() {
//            @Override
//            public void onResponse(Call<TownsResponse> call, Response<TownsResponse> response) {
//                if(response.isSuccessful()){
//                    if(response.body().isSuccess){
//                        Log.e("response.body","success");
//                        townAdapter.addItem(response.body().towns);
//                        Log.e("size", String.valueOf(town.size()));
//                        adapter.notifyDataSetChanged();
//
////                        dialog.show();
//                    }
//                    else {
//                        Log.e("response.body","fail");
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TownsResponse> call, Throwable t) {
//                Log.e("failure",t.toString());
//            }
//        });

    }

    @Override
    public void onTownClick(int id) {

    }
}
