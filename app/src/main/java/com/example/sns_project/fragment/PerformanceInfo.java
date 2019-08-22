package com.example.sns_project.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sns_project.DataInfo;
import com.example.sns_project.R;

import java.util.ArrayList;


public class PerformanceInfo extends Fragment {

    private ArrayList<String> title= new ArrayList<>();
    private ArrayList<String> startDate = new ArrayList<>();
    private ArrayList<String> endDate = new ArrayList<>();
    private ArrayList<String> place = new ArrayList<>();
    private ArrayList<String> realmNama = new ArrayList<>();
    private ArrayList<String> area = new ArrayList<>();
    private ArrayList<String> thumbNail = new ArrayList<>();
    private ListView mListView;;
    private ArrayAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void init(){
        title = DataInfo.getTitle();
        startDate = DataInfo.getStartDate();
        endDate = DataInfo.getEndDate();
        place = DataInfo.getPlace();
        realmNama = DataInfo.getRealmNama();
        area = DataInfo.getArea();
        thumbNail = DataInfo.getThumbNail();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_performance_info, container, false);
        ViewFlipper flipper;
        super.onCreate(savedInstanceState);
        Background thread = new Background();
        thread.start();
        init();
        for(int i=0; i<title.size(); i++){
            System.out.println("title : " + title.get(i));
        }
        mListView=  view.findViewById(R.id.listView);
        mAdapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, title);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        flipper= view.findViewById(R.id.flipper);
        /*for(int i=1;i<5;i++){
            ImageView img = new ImageView(getActivity());
            img.setImageResource(R.drawable.title1+i);
            flipper.addView(img);
        }*/
        Animation showIn= AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        flipper.setInAnimation(showIn);
        flipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
        flipper.setFlipInterval(2000);//플리핑 간격(1000ms)
        flipper.startFlipping();//자동 Flipping 시작
        return view;
    }

    class Background extends Thread{
        @Override
        public void run(){
            super.run();
            DataInfo.getData();
        }
    }

}

