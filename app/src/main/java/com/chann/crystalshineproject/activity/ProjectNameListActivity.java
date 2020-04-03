package com.chann.crystalshineproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.adapter.ProjectNameListAdapter;
import com.chann.crystalshineproject.data.Logout;
import com.chann.crystalshineproject.data.ProjectNameList;
import com.chann.crystalshineproject.data.ProjectNameListResponse;
import com.chann.crystalshineproject.data.Projects;
import com.chann.crystalshineproject.data.Token;
import com.chann.crystalshineproject.holder.ProjectNameListHolder;
import com.chann.crystalshineproject.service.RetrofitService;
import com.chann.crystalshineproject.viewmodel.ProjectNameListViewModel;
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

public class ProjectNameListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ProjectNameListHolder.OnProjectNameItemClickListener {

    private RecyclerView recyclerView;
    private ProjectNameListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private String token = null;
    List<Projects> projects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_project_name_list);

        init();
    }

    private void init() {

        mDrawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.projectNameList_recyclerView);
        adapter = new ProjectNameListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        Bundle b = getIntent().getExtras();
        token = b.getString("Token");

        initActivity();
        initApi();
    }

    private void initApi() {
        Log.e("api","ok");
        RetrofitService.getApiEnd().projectNameList(token).enqueue(new Callback<ProjectNameListResponse>() {
            @Override
            public void onResponse(Call<ProjectNameListResponse> call, Response<ProjectNameListResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");
                        adapter.addItem(response.body().projectNameList.data);
                        Log.e("size", String.valueOf(projects.size()));
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Log.e("response.body","fail");
                    }
                }else {
                    Toast.makeText(getApplicationContext(), response.body().errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProjectNameListResponse> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });
    }

    private void initActivity() {

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);

        }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onProjectListClick(int id) {
        Intent intent = new Intent(getApplicationContext(), TownshipListActivity.class);
        intent.putExtra("Token", token);
        intent.putExtra("projectId", id);
        Log.e("projectId",String.valueOf(id));
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.e("nav","select");
        int id = menuItem.getItemId();

        Intent intent;
        switch (id){
            case 0:
                intent = new Intent(getApplicationContext(), ProjectNameListActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(getApplicationContext(), TownshipListActivity.class);
                startActivity(intent);
                break;
        }

//        if (id == R.id.projectList) {
//            Toast.makeText(this, "Projects", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(), ProjectNameListActivity.class));
//        } else if (id == R.id.township) {
//            Toast.makeText(this, "Township", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(), TownshipListActivity.class));
//        } else if (id == R.id.shopList) {
//            Toast.makeText(this, "Shop List", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(), ShopListActivity.class));
//        } else if (id == R.id.nav_logout) {
//            RetrofitService.getApiEnd().userLogout(token).enqueue(new Callback<Logout>() {
//                @Override
//                public void onResponse(Call<Logout> call, Response<Logout> response) {
//                    if(response.isSuccessful()){
//                        if (response.body().isSuccess) {
//                            Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_LONG).show();
//                        } else {
//                            Log.e("response.body","fail");
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Logout> call, Throwable t) {
//                    Log.e("failure","fail");
//                }
//            });
//            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
//            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);// 0 - for private mode
//            SharedPreferences.Editor editor = pref.edit();
//            editor.clear();
//            editor.commit();
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
