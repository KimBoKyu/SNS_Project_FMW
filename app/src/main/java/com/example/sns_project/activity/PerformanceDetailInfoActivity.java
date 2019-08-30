package com.example.sns_project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.sns_project.APIData;
import com.example.sns_project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class PerformanceDetailInfoActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private TextView textViewSeqNum;
    private ArrayList<String> detailInfo;
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
        detailInfo = APIData.getDetailInfo();
        for(int i=0; i<detailInfo.size(); i++){
            System.out.println(detailInfo.get(i));
        }
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

    public void gotoLink(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ticketlink.co.kr/product/15363"));
        startActivity(intent);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng SEOUL = new LatLng(37.5657855355, 126.967578144);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
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