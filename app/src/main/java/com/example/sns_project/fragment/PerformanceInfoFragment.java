package com.example.sns_project.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sns_project.APIData;
import com.example.sns_project.PerformanceInfo;
import com.example.sns_project.R;
import com.example.sns_project.activity.PerformanceDetailInfoActivity;
import com.example.sns_project.adapter.RecyclerViewAdapter;
import com.example.sns_project.adapter.RecyclerViewHolder;

import java.util.ArrayList;


public class PerformanceInfoFragment extends Fragment {
    private static final String TAG = "PerformanceInfoFragment";
    private ArrayList<PerformanceInfo> performanceInfos;
    private ImageView imgPerformance;
    private RecyclerViewAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_performance_info, container, false);
        super.onCreate(savedInstanceState);
        performanceInfos = APIData.getPerformanceInfos();
        imgPerformance = view.findViewById(R.id.imgRandom);
        for(int count = 0; ;count++){
            if(performanceInfos.get(count).getThumbNail() != null){
                Glide.with(getActivity()).load(performanceInfos.get(count).getThumbNail()).into(imgPerformance);
                break;
            }
        }
        RecyclerView recyclerView;
        recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(getContext()); //여기애매함
        adapter.addItems(performanceInfos);
        recyclerView.setAdapter(adapter);   // 어뎁터 설정

        adapter.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
            @Override
            public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                myStartActivity(PerformanceDetailInfoActivity.class, position);
            }
        });
        return view;
    }

    private void myStartActivity(Class c, int postion) {
        Intent intent = new Intent(getActivity(), c);
        intent.putExtra("seqNum", performanceInfos.get(postion).getSeqNum());
        startActivityForResult(intent, 0);
    }

}

