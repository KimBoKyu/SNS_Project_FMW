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
    private ArrayList<PerformanceInfo> koreaMusicInfos = new ArrayList<>();
    private ArrayList<PerformanceInfo> musicInfos = new ArrayList<>();
    private ArrayList<PerformanceInfo> theaterInfos = new ArrayList<>();
    private ArrayList<PerformanceInfo> dancingInfos = new ArrayList<>();
    private ArrayList<PerformanceInfo> artInfos = new ArrayList<>();
    private ArrayList<PerformanceInfo> otherInfos = new ArrayList<>();
    private ImageView imgPerformance;
    private RecyclerViewAdapter adapter1;
    private RecyclerViewAdapter adapter2;
    private RecyclerViewAdapter adapter3;
    private RecyclerViewAdapter adapter4;
    private RecyclerViewAdapter adapter5;
    private RecyclerViewAdapter adapter6;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private RecyclerView recyclerView6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_performance_info, container, false);
        super.onCreate(savedInstanceState);
        arrayInit();
        init(view);
        settingImg();
        System.out.println(otherInfos.size());
        return view;
    }

    public void sortArray(ArrayList<PerformanceInfo> arr){
        Collections.sort(arr, new Comparator<PerformanceInfo>() {
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
    }

    public void arrayInit(){
        performanceInfos = APIData.getPerformanceInfos();
        usingPerformanceInfos = settingPerformaceArray();
        for(int i=0; i<usingPerformanceInfos.size(); i++){
            switch (usingPerformanceInfos.get(i).getRealmName()){
                case "음악":
                    musicInfos.add(usingPerformanceInfos.get(i));
                    break;
                case "연극":
                    theaterInfos.add(usingPerformanceInfos.get(i));
                    break;
                case "미술":
                    artInfos.add(usingPerformanceInfos.get(i));
                    break;
                case "무용":
                    dancingInfos.add(usingPerformanceInfos.get(i));
                    break;
                case "국악":
                    koreaMusicInfos.add(usingPerformanceInfos.get(i));
                    break;
                default:
                    otherInfos.add(usingPerformanceInfos.get(i));
                    break;
            }
        }
        sortArray(musicInfos);
        sortArray(artInfos);
        sortArray(theaterInfos);
        sortArray(artInfos);
        sortArray(dancingInfos);
        sortArray(koreaMusicInfos);
        sortArray(otherInfos);
    }


    public void init(View view){
        imgPerformance = view.findViewById(R.id.imgRandom);
        imgPerformance.setOnClickListener(onClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager6 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView1 = view.findViewById(R.id.recycler_view1);
        recyclerView2 = view.findViewById(R.id.recycler_view2);
        recyclerView3 = view.findViewById(R.id.recycler_view3);
        recyclerView4 = view.findViewById(R.id.recycler_view4);
        recyclerView5 = view.findViewById(R.id.recycler_view5);
        recyclerView6 = view.findViewById(R.id.recycler_view6);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView4.setLayoutManager(layoutManager4);
        recyclerView5.setLayoutManager(layoutManager5);
        recyclerView6.setLayoutManager(layoutManager6);

        adapter1 = new RecyclerViewAdapter(getContext()); //여기애매함
        adapter1.addItems(musicInfos);
        recyclerView1.setAdapter(adapter1);   // 어뎁터 설정

        adapter2 = new RecyclerViewAdapter(getContext()); //여기애매함
        adapter2.addItems(theaterInfos);
        recyclerView2.setAdapter(adapter2);   // 어뎁터 설정

        adapter3 = new RecyclerViewAdapter(getContext()); //여기애매함
        adapter3.addItems(artInfos);
        recyclerView3.setAdapter(adapter3);   // 어뎁터 설정

        adapter4 = new RecyclerViewAdapter(getContext()); //여기애매함
        adapter4.addItems(dancingInfos);
        recyclerView4.setAdapter(adapter4);   // 어뎁터 설정

        adapter5 = new RecyclerViewAdapter(getContext()); //여기애매함
        adapter5.addItems(koreaMusicInfos);
        recyclerView5.setAdapter(adapter5);   // 어뎁터 설정

        adapter6 = new RecyclerViewAdapter(getContext()); //여기애매함
        adapter6.addItems(otherInfos);
        recyclerView6.setAdapter(adapter6);   // 어뎁터 설정


        adapter1.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
            @Override
            public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                myStartActivity(PerformanceDetailInfoActivity.class, musicInfos,position);
            }
        });

        adapter2.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
            @Override
            public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                myStartActivity(PerformanceDetailInfoActivity.class, theaterInfos,position);
            }
        });
        adapter3.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
            @Override
            public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                myStartActivity(PerformanceDetailInfoActivity.class, artInfos,position);
            }
        });
        adapter4.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
            @Override
            public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                myStartActivity(PerformanceDetailInfoActivity.class, dancingInfos,position);
            }
        });
        adapter5.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
            @Override
            public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                myStartActivity(PerformanceDetailInfoActivity.class, koreaMusicInfos,position);
            }
        });
        adapter6.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
            @Override
            public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                myStartActivity(PerformanceDetailInfoActivity.class, otherInfos,position);
            }
        });
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
        }
        if(usingPerformanceInfos.size() == 0){
            usingPerformanceInfos = performanceInfos;
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

