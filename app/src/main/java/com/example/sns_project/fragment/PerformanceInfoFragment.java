package com.example.sns_project.fragment;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class PerformanceInfoFragment extends Fragment {
    private static final String TAG = "PerformanceInfoFragment";
    private final int max_count = 50;
    private ArrayList<PerformanceInfo> performanceInfos;
    private ArrayList<PerformanceInfo> usingPerformanceInfos = new ArrayList<>();
    private ImageView imgPerformance;
    private RecyclerViewAdapter adapter;
    private EditText searchEditText;
    private Button searchButton;
    private RelativeLayout loaderLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_performance_info, container, false);
        super.onCreate(savedInstanceState);
        loaderLayout = view.findViewById(R.id.loaderLyaout);
        performanceInfos = APIData.getPerformanceInfos();
        usingPerformanceInfos = settingPerformaceArray();
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
            }
        });
        imgPerformance = view.findViewById(R.id.imgRandom);
        imgPerformance.setOnClickListener(onClickListener);
        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(onClickListener);
        searchEditText = view.findViewById(R.id.searchEditText);
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
                myStartActivity(PerformanceDetailInfoActivity.class, usingPerformanceInfos,position);
            }
        });
        settingImg();
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgRandom:
                    settingImg();
                    break;
                case R.id.searchButton:
                    loaderLayout.setVisibility(View.VISIBLE);
                    searching();

                    break;
            }
        }
    };

    private void searching(){
        String temp = String.valueOf(searchEditText.getText());
        for(int i=0; i<performanceInfos.size(); i++){
            if(performanceInfos.get(i).getTitle().contains(temp)){
                loaderLayout.setVisibility(View.GONE);
                myStartActivity(PerformanceDetailInfoActivity.class, performanceInfos, i);
                break;
            }
        }

    }

    private void settingImg(){
        Random random = new Random();
        while(true){
            int rand = random.nextInt(usingPerformanceInfos.size());
            if(usingPerformanceInfos.get(rand).getThumbNail() != null){
                Glide.with(getActivity()).load(usingPerformanceInfos.get(rand).getThumbNail()).override(2000).into(imgPerformance);
                break;
            }
        }
    }

    private ArrayList<PerformanceInfo> settingPerformaceArray(){
        int count = 0;
        ArrayList<PerformanceInfo> usingPerformanceInfos = new ArrayList<>();
        Location myPos = new Location("MyPos");
        Location performancePos = new Location("PerPos");
        // GpsX = Latitude , GpsY = Longitude
        //myPos.setLongitude(Util.myPosY);
        //myPos.setLatitude(Util.myPosX);
        myPos.setLatitude(37.602938);
        myPos.setLongitude(126.955007);
        for(int i=0; i<performanceInfos.size(); i++){
            performancePos.setLatitude(Double.parseDouble(performanceInfos.get(i).getGpsY()));
            performancePos.setLongitude(Double.parseDouble(performanceInfos.get(i).getGpsX()));
            double temp = myPos.distanceTo(performancePos)/1000;
            performanceInfos.get(i).setDistance(String.format("%.2f", temp));
        }
        for(int i=0; i<performanceInfos.size(); i++){
            if(Double.parseDouble(performanceInfos.get(i).getDistance()) < 20.0){
                    usingPerformanceInfos.add(performanceInfos.get(i));
            }
            if(count++ >= max_count){
                break;
            }
        }
        if(usingPerformanceInfos.size() == 0){
            for(int i=0; i<max_count; i++){
                usingPerformanceInfos.add(performanceInfos.get(i));
            }
        }
        for(int i=1; i<usingPerformanceInfos.size(); i++){
            if(usingPerformanceInfos.get(i).getTitle().equals(usingPerformanceInfos.get(i-1).getTitle())){
                usingPerformanceInfos.remove(i);
            }
        }


        return usingPerformanceInfos;
    }

    private void myStartActivity(Class c, ArrayList<PerformanceInfo> performanceInfos ,int postion) {
        Intent intent = new Intent(getActivity(), c);
        intent.putExtra("thumbNail", performanceInfos.get(postion).getThumbNail());
        intent.putExtra("seqNum", performanceInfos.get(postion).getSeqNum());
        intent.putExtra("title", performanceInfos.get(postion).getTitle());
        intent.putExtra("startDate", performanceInfos.get(postion).getStartDate());
        intent.putExtra("endDate", performanceInfos.get(postion).getEndDate());
        intent.putExtra("realmName", performanceInfos.get(postion).getRealmName());
        intent.putExtra("gpsX", performanceInfos.get(postion).getGpsX());
        intent.putExtra("gpsY", performanceInfos.get(postion).getGpsY());
        startActivityForResult(intent, 0);
    }

}

