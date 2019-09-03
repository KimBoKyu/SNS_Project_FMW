package com.example.sns_project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.sns_project.APIData;
import com.example.sns_project.PerformanceDetailInfo;
import com.example.sns_project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PerformanceDetailInfoActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private TextView textViewSeqNum;
    private PerformanceDetailInfo performanceDetailInfo;
    private double gpsY;
    private double gpsX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Background thread = new Background();
        thread.start();
        try{
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        performanceDetailInfo = APIData.getDetailInfo();
        System.out.println(performanceDetailInfo.getPrice());
        System.out.println(performanceDetailInfo.getGpsx());
        System.out.println(performanceDetailInfo.getGpsy());
        System.out.println(performanceDetailInfo.getUrl());
        System.out.println(performanceDetailInfo.getPlaceAddr());
        setContentView(R.layout.activity_performance_detail);
        textViewSeqNum = findViewById(R.id.textSeqNum);
        textViewSeqNum.setText(getIntent().getStringExtra("seqNum"));
        findViewById(R.id.buttonBuyLink).setOnClickListener(onClickListener);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonBuyLink:
                    gotoLink();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed(){
        finish();
    }

    public void gotoLink(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(performanceDetailInfo.getUrl()));
        startActivity(intent);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        gpsY = Double.parseDouble(performanceDetailInfo.getGpsy());
        gpsX = Double.parseDouble(performanceDetailInfo.getGpsx());
        LatLng addr = new LatLng(gpsY, gpsX);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(addr);
        markerOptions.title("공연장소");
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(addr));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }


    class Background extends Thread{
        @Override
        public void run(){
            super.run();
            APIData.getDetailData(getIntent().getStringExtra("seqNum"));
        }
    }
}