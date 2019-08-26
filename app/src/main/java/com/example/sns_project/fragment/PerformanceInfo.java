package com.example.sns_project.fragment;


import android.content.Intent;
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
import com.example.sns_project.activity.PerformanceDetailInfoActivity;

import java.util.ArrayList;


public class PerformanceInfo extends Fragment {
    private static final String TAG = "PerformanceInfo";
    private ArrayList<String> title= new ArrayList<>();
    private ArrayList<String> startDate = new ArrayList<>();
    private ArrayList<String> endDate = new ArrayList<>();
    private ArrayList<String> place = new ArrayList<>();
    private ArrayList<String> realmNama = new ArrayList<>();
    private ArrayList<String> area = new ArrayList<>();
    private ArrayList<String> thumbNail = new ArrayList<>();
    private ArrayList<String> seqNum = new ArrayList<>();
    private ListView mListView;;
    private ArrayAdapter mAdapter;
    private ArrayList<String> mList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void init(){
        title = DataInfo.getTitle();
        startDate = DataInfo.getStartDate();
        endDate = DataInfo.getEndDate();
        place = DataInfo.getPlace();
        realmNama = DataInfo.getRealmName();
        area = DataInfo.getArea();
        thumbNail = DataInfo.getThumbNail();
        seqNum = DataInfo.getSeqNum();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_performance_info, container, false);
        ViewFlipper flipper;
        view.findViewById(R.id.button).setOnClickListener(onClickListener);
        super.onCreate(savedInstanceState);
        init();
        for(int i=0; i<title.size(); i++){
            System.out.println("title : " + title.get(i));
        }
        mListView=  view.findViewById(R.id.listView);
        mAdapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        for(int i=0; i<title.size(); i++){
            mList.add(title.get(i));
        }

        flipper= view.findViewById(R.id.flipper);
        Animation showIn= AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        flipper.setInAnimation(showIn);
        flipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
        flipper.setFlipInterval(2000);//플리핑 간격(1000ms)
        flipper.startFlipping();//자동 Flipping 시작
        return view;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button:
                    myStartActivity(PerformanceDetailInfoActivity.class );
                    break;
            }
        }
    };

    private void myStartActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivityForResult(intent, 0);
    }

}

