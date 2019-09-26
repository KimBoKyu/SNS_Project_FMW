package com.example.sns_project.fragment;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sns_project.APIData;
import com.example.sns_project.PerformanceInfo;
import com.example.sns_project.R;
import com.example.sns_project.Util;
import com.example.sns_project.activity.PerformanceDetailInfoActivity;
import com.example.sns_project.adapter.RecyclerViewAdapter;
import com.example.sns_project.adapter.RecyclerViewHolder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class PerformanceInfoFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "PerformanceInfoFragment";
    private GoogleMap mMap;
    private ArrayList<PerformanceInfo> performanceInfos;
    private ArrayList<PerformanceInfo> usingPerformanceInfos;
    private ArrayList<PerformanceInfo> koreaMusicInfos = new ArrayList<>();
    private ArrayList<PerformanceInfo> musicInfos = new ArrayList<>();
    private ArrayList<PerformanceInfo> theaterInfos = new ArrayList<>();
    private ArrayList<PerformanceInfo> dancingInfos = new ArrayList<>();
    private ArrayList<PerformanceInfo> artInfos = new ArrayList<>();
    private ArrayList<PerformanceInfo> otherInfos = new ArrayList<>();
    private ArrayList<String> genres = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private RecyclerView performance_recyclerView;
    private Spinner genreSpinner;
    private TextView genreTextView;
    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_performance_info, container, false);
        super.onCreate(savedInstanceState);
        mapView = (MapView)view.findViewById(R.id.mapView_performance);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        arrayInit();
        genreSpinner = view.findViewById(R.id.genre_spinner);
        genreTextView = view.findViewById(R.id.genre_textview);
        genres = APIData.getGenres();
        ArrayAdapter adp = new ArrayAdapter(getContext(), R.layout.spinner_item, genres);
        adp.setDropDownViewResource(R.layout.spinner_item);
        genreSpinner.setAdapter(adp);
        genreSpinner.setSelection(0);
        genreTextView.setText(genres.get(0));
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(genreSpinner.getSelectedItemPosition() > 0){
                    genreTextView.setText(genreSpinner.getSelectedItem().toString());
                    switch (genreSpinner.getSelectedItem().toString()){
                        case "음악":
                            settingRecycleView(view, musicInfos);
                            break;
                        case "연극":
                            settingRecycleView(view, theaterInfos);
                            break;
                        case "기타":
                            settingRecycleView(view, otherInfos);
                            break;
                        case "미술":
                            settingRecycleView(view, artInfos);
                            break;
                        case "무용":
                            settingRecycleView(view, dancingInfos);
                            break;
                        case "국악":
                            settingRecycleView(view, koreaMusicInfos);
                            break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        performance_recyclerView = view.findViewById(R.id.performance_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),3);
        performance_recyclerView.setLayoutManager(gridLayoutManager);


        adapter = new RecyclerViewAdapter(getContext()); //여기애매함
        adapter.addItems(koreaMusicInfos);
        performance_recyclerView.setAdapter(adapter);   // 어뎁터 설정
        adapter.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
            @Override
            public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                myStartActivity(PerformanceDetailInfoActivity.class, koreaMusicInfos, position);
            }
        });
        return view;
    }

    public void settingRecycleView(View view, final ArrayList<PerformanceInfo> arr){
        adapter.replaceItems(arr);
        performance_recyclerView.setAdapter(adapter);   // 어뎁터 설정
        adapter.setOnItemClickListener(new RecyclerViewAdapter.itemClickListener() {
            @Override
            public void onItemClick(RecyclerViewHolder holder, View view, int position) {
                myStartActivity(PerformanceDetailInfoActivity.class, arr, position);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("수도");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
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

    private ArrayList<PerformanceInfo> settingPerformaceArray(){
        ArrayList<PerformanceInfo> usingPerformanceInfos = new ArrayList<>();
        Location myPos = new Location("MyPos");
        Location performancePos = new Location("PerPos");
        myPos.setLongitude(Util.myPosY);
        myPos.setLatitude(Util.myPosX);
        for(int i=0; i<performanceInfos.size(); i++){
            performancePos.setLatitude(Double.parseDouble(performanceInfos.get(i).getGpsY()));
            performancePos.setLongitude(Double.parseDouble(performanceInfos.get(i).getGpsX()));
            double temp = myPos.distanceTo(performancePos)/1000;
            performanceInfos.get(i).setDistance(String.format("%.2f", temp));
        }
        if(usingPerformanceInfos.size() == 0){
            usingPerformanceInfos = performanceInfos;
        }
        else{
            for(int i=0; i<performanceInfos.size(); i++){
                if(Double.parseDouble(performanceInfos.get(i).getDistance()) < 20.0){
                    usingPerformanceInfos.add(performanceInfos.get(i));
                }
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

