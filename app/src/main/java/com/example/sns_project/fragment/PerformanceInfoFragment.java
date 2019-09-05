package com.example.sns_project.fragment;


import android.content.Intent;
import android.location.Location;
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
import com.example.sns_project.Util;
import com.example.sns_project.activity.PerformanceDetailInfoActivity;
import com.example.sns_project.adapter.RecyclerViewAdapter;
import com.example.sns_project.adapter.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class PerformanceInfoFragment extends Fragment {
    private static final String TAG = "PerformanceInfoFragment";
    private ArrayList<PerformanceInfo> performanceInfos;
    private ArrayList<PerformanceInfo> usingPerformanceInfos = new ArrayList<>();
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
        settingPerformaceArray();
        Collections.sort(usingPerformanceInfos, new Comparator<PerformanceInfo>() {
            @Override
            public int compare(PerformanceInfo o1, PerformanceInfo o2) {
                if(Float.parseFloat(o1.getDistance()) > Float.parseFloat(o2.getDistance())){
                    return 1;
                }
                else if(Float.parseFloat(o1.getDistance()) == Float.parseFloat(o2.getDistance())){
                    return 0;
                }
                else {
                    return -1;
                }
                //return Float.parseFloat(o1.getDistance()).compareTo(Float.parseFloat(o2.getDistance()));
            }
        });
        imgPerformance = view.findViewById(R.id.imgRandom);
        imgPerformance.setOnClickListener(onClickListener);
        settingImg();
        RecyclerView recyclerView;
        recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(getContext()); //여기애매함
        adapter.addItems(usingPerformanceInfos);
        recyclerView.setAdapter(adapter);   // 어뎁터 설정
        adapter.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
            @Override
            public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                myStartActivity(PerformanceDetailInfoActivity.class, position);
            }
        });
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgRandom:
                    settingImg();
                    break;
            }
        }
    };

    public void settingImg(){
        Random random = new Random();
        while(true){
            int rand = random.nextInt(usingPerformanceInfos.size());
            if(usingPerformanceInfos.get(rand).getThumbNail() != null){
                Glide.with(getActivity()).load(performanceInfos.get(rand).getThumbNail()).into(imgPerformance);
                break;
            }
        }
    }

    public void settingPerformaceArray(){
        Location myPos = new Location("MyPos");
        Location performancePos = new Location("PerPos");
        // GpsX = Latitude , GpsY = Longitude
        myPos.setLongitude(Util.myPosY);
        myPos.setLatitude(Util.myPosX);
        //myPos.setLatitude(37.602938);
        //myPos.setLongitude(126.955007);
        for(int i=0; i<APIData.rows; i++){
            performancePos.setLatitude(Double.parseDouble(performanceInfos.get(i).getGpsY()));
            performancePos.setLongitude(Double.parseDouble(performanceInfos.get(i).getGpsX()));
            double temp = myPos.distanceTo(performancePos)/1000;
            performanceInfos.get(i).setDistance(String.format("%.2f", temp));
        }

        for(int i=0; i<performanceInfos.size(); i++){
            if(Double.parseDouble(performanceInfos.get(i).getDistance()) < 20.0){
                usingPerformanceInfos.add(performanceInfos.get(i));
            }
        }
    }

    private void myStartActivity(Class c, int postion) {
        Intent intent = new Intent(getActivity(), c);
        intent.putExtra("thumbNail", usingPerformanceInfos.get(postion).getThumbNail());
        intent.putExtra("seqNum", usingPerformanceInfos.get(postion).getSeqNum());
        intent.putExtra("title", usingPerformanceInfos.get(postion).getTitle());
        intent.putExtra("startDate", usingPerformanceInfos.get(postion).getStartDate());
        intent.putExtra("endDate", usingPerformanceInfos.get(postion).getEndDate());
        intent.putExtra("realmName", usingPerformanceInfos.get(postion).getRealmName());
        intent.putExtra("gpsX", usingPerformanceInfos.get(postion).getGpsX());
        intent.putExtra("gpsY", usingPerformanceInfos.get(postion).getGpsY());
        startActivityForResult(intent, 0);
    }

}

