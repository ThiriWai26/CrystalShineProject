//package com.chann.crystalshineproject.fragment;
//
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.chann.crystalshineproject.R;
//import com.chann.crystalshineproject.adapter.TownshipListAdapter;
//import com.chann.crystalshineproject.holder.TownshipListHolder;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class TownshipListFragment extends Fragment implements TownshipListHolder.OnTownshipItemClickListener {
//
//    private RecyclerView recyclerView;
//    private TownshipListAdapter adapter;
//
//    public TownshipListFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_township_list, container, false);
//
//        recyclerView = view.findViewById(R.id.projectNameList_recyclerView);
//        adapter = new TownshipListAdapter(this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        return view;
//    }
//
//}
