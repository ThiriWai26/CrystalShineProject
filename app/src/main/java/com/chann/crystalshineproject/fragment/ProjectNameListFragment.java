package com.chann.crystalshineproject.fragment;


import android.os.Bundle;

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
import com.chann.crystalshineproject.viewmodel.ProjectNameListViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectNameListFragment extends Fragment  {

    private ProjectNameListViewModel model;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ProjectNameListAdapter adapter;

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
        progressBar = view.findViewById(R.id.progressBar);
//        adapter = new ProjectNameListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        model = ViewModelProviders.of(this).get(ProjectNameListViewModel.class);

//        model.getMatchList(Token.today).observe(this, matchListResponse -> {
//            progressBar.setVisibility(View.GONE);
//            if (matchListResponse.isSuccess) {

//                adapter.addData(matchListResponse.matchListData);
//                adapter.notifyDataSetChanged();
//            } else {
//                Toast.makeText(getContext(), "Response not successful", Toast.LENGTH_LONG).show();
//            }
//        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        model.compositeDisposable.clear();
//        model.disposable.dispose();
    }


//    @Override
//    public void onProjectNameListClick(int id) {
//        FragmentRecentBookDetail fragmentRecentBookDetail=new FragmentRecentBookDetail();
//        Bundle bundle=new Bundle();
//        bundle.putInt("book_id",id);
//        fragmentRecentBookDetail.setArguments(bundle);
//        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frame,fragmentRecentBookDetail).commit();
//
//        Log.e("book_list_id",String.valueOf(id));
//    }


}
