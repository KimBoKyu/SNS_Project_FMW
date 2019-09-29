package com.example.sns_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.example.sns_project.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PerformanceDetailInfoActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private TextView textViewTitle;
    private TextView textViewPeroid;
    private TextView textViewPlace;
    private TextView textViewPrice;
    private PerformanceDetailInfo performanceDetailInfo;
    private float gpsY;
    private float gpsX;
    private String thumbNail;
    private String contents1;
    private ImageView img_only;
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
        contents1 = performanceDetailInfo.getContents1();
        System.out.println(contents1);
        img_only = findViewById(R.id.image_only2);
        textViewTitle = findViewById(R.id.textTitle);
        performanceImageView = findViewById(R.id.performanceImageView);
        textViewPeroid = findViewById(R.id.textPeroid);
        textViewPlace = findViewById(R.id.textPlace);
        textViewPrice = findViewById(R.id.textPrice);
        performanceImageView.setOnClickListener(onClickListener);
        img_only.setOnClickListener(onClickListener);
    }

    public void textSetting(){
        Glide.with(this).load(thumbNail).into(performanceImageView);
        textViewTitle.setText(getIntent().getStringExtra("title"));
        textViewPrice.setText(performanceDetailInfo.getPrice());
        textViewPeroid.setText(getIntent().getStringExtra("startDate") + " ~ " + getIntent().getStringExtra("endDate"));
        textViewPlace.setText(performanceDetailInfo.getPlace());
        System.out.println(getIntent().getStringExtra("seqNum"));
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonBuyLink:
                    gotoLink();
                    break;
                case R.id.performanceImageView:
                    gotoImgOnly();
                    break;
                case R.id.image_only2:
                    img_only.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };

    @Override
    public void onBackPressed(){
        finish();
    }

    public void gotoLink(){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(performanceDetailInfo.getUrl()));
            startActivity(intent);
        } catch (Exception e){
            e.printStackTrace();
            Util.showToast(this, "해당되는 페이지가 없습니다. 죄송합니다.");
        }

    }

    public void gotoImgOnly(){
        Glide.with(this).load(thumbNail).into(img_only);
        img_only.setVisibility(View.VISIBLE);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng addr = new LatLng(gpsY, gpsX);
        System.out.println(gpsX + "      " + gpsY);
        if(gpsY < 33 || gpsY >43 || gpsX < 124 || gpsX > 132){
            Util.showToast(this, "주어진 위치 정보가 없습니다.");
        }
        else{
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(addr);
            if(performanceDetailInfo.getPlaceAddr().length() <=5 ){
                markerOptions.title(performanceDetailInfo.getPlace());
            }
            else{
                markerOptions.title(performanceDetailInfo.getPlaceAddr());
            }
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.point);
            Bitmap b=bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addr, 15));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }


    class Background extends Thread{
        @Override
        public void run(){
            super.run();
            APIData.getDetailData(getIntent().getStringExtra("seqNum"));
        }
    }
}