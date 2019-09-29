package com.example.sns_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sns_project.APIData;
import com.example.sns_project.PerformanceInfo;
import com.example.sns_project.R;
import com.example.sns_project.Util;
import com.example.sns_project.activity.PerformanceDetailInfoActivity;
import com.example.sns_project.adapter.RecyclerViewAdapter;
import com.example.sns_project.adapter.RecyclerViewHolder;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private ArrayList<PerformanceInfo> performanceInfos;
    private EditText textSearch;
    private ArrayList<PerformanceInfo> performanceInfosSearch;
    private RecyclerView performance_recyclerView_search;
    private RecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        performanceInfos = APIData.getPerformanceInfos();
        textSearch = view.findViewById(R.id.searchEditText);
        textSearch.setFocusableInTouchMode(true);
        if(textSearch.requestFocus()){
            Util.upKeyboard(getContext());
        }
        performance_recyclerView_search = view.findViewById(R.id.performance_recyclerView_search);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        performance_recyclerView_search.setLayoutManager(gridLayoutManager);
        adapter = new RecyclerViewAdapter(getContext()); //여기애매함
        view.findViewById(R.id.searchButton).setOnClickListener(onClickListener);
        return view;
    }


    public void search(View view){
        boolean flag1 = false;
        boolean flag2 = false;
        performanceInfosSearch = new ArrayList<>();
        String search_inp = textSearch.getText().toString();
        if(search_inp.length() < 2){
            Util.showToast(getActivity(), "두글자 이상 검색해주세요.");
        }else{
            flag1 = true;
            for(int i=0; i<performanceInfos.size(); i++){
                if(performanceInfos.get(i).getTitle().contains(search_inp)){
                    performanceInfosSearch.add(performanceInfos.get(i));
                }
            }
            if(performanceInfosSearch.size() == 0){
                Util.showToast(getActivity(), "검색 결과가 없습니다.");
            }
            else{
                flag2 = true;
            }
        }
        if(flag1 && flag2) {
            adapter.addItems(performanceInfosSearch);
            performance_recyclerView_search.setAdapter(adapter);   // 어뎁터 설정
            adapter.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
                @Override
                public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                    myStartActivity(PerformanceDetailInfoActivity.class, performanceInfosSearch, position);
                }
            });
        }
    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.searchButton:
                    search(getView());
                    Util.downKeyboard(getContext(), textSearch);
                    break;
            }
        }
    };

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
