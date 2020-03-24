package com.chann.crystalshineproject.activity;

import androidx.annotation.NonNull;
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

        recyclerView = findViewById(R.id.projectNameList_recyclerView);
        adapter = new ProjectNameListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        token = Token.MyToken.getToken();
        Log.e("token", token);

        initActivity();
        initApi();
    }

    private void initApi() {

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
    public void onProjectListClick(int id) {
        Intent intent = new Intent(getApplicationContext(), ShopListActivity.class);
        intent.putExtra("projectId", id);
        Log.e("projectId",String.valueOf(id));
        startActivity(intent);
    }

}
