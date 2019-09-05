package com.example.sns_project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
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
    private TextView textViewTitle;
    private TextView textViewStartDate;
    private TextView textViewEndDate;
    private TextView textViewPlace;
    private TextView textViewAddress;
    private TextView textViewRealmName;
    private PerformanceDetailInfo performanceDetailInfo;
    private float gpsY;
    private float gpsX;
    private String thumbNail;
    private ImageView performanceImageView;
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
        setContentView(R.layout.activity_performance_detail);
        init();
        textSetting();
        findViewById(R.id.buttonBuyLink).setOnClickListener(onClickListener);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void init(){
        performanceDetailInfo = APIData.getDetailInfo();
        gpsY = Float.parseFloat(getIntent().getStringExtra("gpsY"));
        gpsX = Float.parseFloat(getIntent().getStringExtra("gpsX"));
        thumbNail = getIntent().getStringExtra("thumbNail");
        textViewTitle = findViewById(R.id.textTitle);
        performanceImageView = findViewById(R.id.performanceImageView);
        textViewStartDate = findViewById(R.id.textStartDate);
        textViewEndDate = findViewById(R.id.textEndDate);
        textViewPlace = findViewById(R.id.textPlace);
        textViewAddress = findViewById(R.id.textAddress);
        textViewRealmName = findViewById(R.id.textRealmName);
    }

    public void textSetting(){
        Glide.with(this).load(thumbNail).into(performanceImageView);
        textViewTitle.setText(getIntent().getStringExtra("title"));
        textViewAddress.setText(performanceDetailInfo.getPlaceAddr());
        textViewRealmName.setText(getIntent().getStringExtra("realmName"));
        textViewStartDate.setText(getIntent().getStringExtra("startDate"));
        textViewEndDate.setText(getIntent().getStringExtra("endDate"));
        textViewPlace.setText(performanceDetailInfo.getPrice());
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
        LatLng addr = new LatLng(gpsY, gpsX);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(addr);
        markerOptions.title(performanceDetailInfo.getPlaceAddr().toString());
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addr, 15));
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