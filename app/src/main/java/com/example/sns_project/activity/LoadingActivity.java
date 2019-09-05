package com.example.sns_project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;

import com.example.sns_project.R;
import com.example.sns_project.view.WaveHelper;
import com.gelitenight.waveview.library.WaveView;
import com.example.sns_project.R;

public class LoadingActivity extends Activity {

    private WaveHelper mWaveHelper;

    private int mBorderColor = Color.parseColor("#44FFFFFF");
    private int mBorderWidth = 10;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final WaveView waveView = (WaveView) findViewById(R.id.wave);
        waveView.setBorder(mBorderWidth, mBorderColor);

        mWaveHelper = new WaveHelper(waveView);





        waveView.setWaveColor(Color.parseColor("#1E90FF"),
                Color.parseColor("#b8f1ed"));
        mBorderColor = Color.parseColor("#b8f1ed");
        waveView.setBorder(mBorderWidth, mBorderColor);

        startLoading();
    }

    protected void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWaveHelper.start();
    }




    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 6000);
    }
}

