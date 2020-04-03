package com.chann.crystalshineproject.fragment;


import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.adapter.ProjectNameListAdapter;
import com.chann.crystalshineproject.api.OnProjectNameItemClickListener;
import com.chann.crystalshineproject.data.ProjectNameList;
import com.chann.crystalshineproject.data.Projects;
import com.chann.crystalshineproject.holder.ProjectNameListHolder;
import com.chann.crystalshineproject.viewmodel.ProjectNameListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectNameListFragment extends Fragment implements ProjectNameListHolder.OnProjectNameItemClickListener {

    private RecyclerView recyclerView;
    private ProjectNameListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private String token = null;
    List<Projects> projects = new ArrayList<>();

    public ProjectNameListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_name_list, container, false);
        initFragment(view);
        return view;


    }

    private void initFragment(View view) {

        recyclerView = view.findViewById(R.id.projectNameList_recyclerView);
        adapter = new ProjectNameListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        recyclerView = view.findViewById(R.id.projectNameList_recyclerView);
        adapter = new ProjectNameListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Bundle b = getActivity().getIntent().getExtras();
        token = b.getString("Token");

    }

    @Override
    public void onProjectListClick(int id) {

    }

//    @Override
//    public void onProjectNameListClick(int id) {
//        TownshipListFragment townshipListFragment=new TownshipListFragment();
//        Bundle bundle=new Bundle();
////        bundle.putInt("book_id",id);
//        townshipListFragment.setArguments(bundle);
//        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frame,townshipListFragment).commit();
//
//        Log.e("book_list_id",String.valueOf(id));
//    }


}
