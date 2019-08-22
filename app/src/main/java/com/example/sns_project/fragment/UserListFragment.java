package com.example.sns_project.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sns_project.R;

import java.util.ArrayList;


public class UserListFragment extends Fragment {


    private ArrayList<String> mList;
    private ListView mListView;;
    private ArrayAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ViewFlipper flipper;
        super.onCreate(savedInstanceState);

        mList = new ArrayList<>();
        mListView= (ListView) view.findViewById(R.id.listView);
        mAdapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
        mList.add("bokyu babo");
        mList.add("seongsu gold4");
        mList.add("soojin break");
        mAdapter.notifyDataSetChanged();



        flipper= (ViewFlipper)view.findViewById(R.id.flipper);
        for(int i=1;i<5;i++){
            ImageView img = new ImageView(getActivity());
            img.setImageResource(R.drawable.title1+i);
            flipper.addView(img);
        }
        Animation showIn= AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        flipper.setInAnimation(showIn);
        flipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
        flipper.setFlipInterval(2000);//플리핑 간격(1000ms)
        flipper.startFlipping();//자동 Flipping 시작




        return view;

    }



}

