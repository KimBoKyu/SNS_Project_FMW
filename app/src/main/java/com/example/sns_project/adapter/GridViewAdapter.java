package com.example.sns_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sns_project.PerformanceInfo;
import com.example.sns_project.R;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        private ArrayList<PerformanceInfo> performanceInfo;
        private LayoutInflater inf;

    public GridViewAdapter(Context context, int layout, ArrayList<PerformanceInfo> performanceInfo) {
        this.context = context;
        this.layout = layout;
        this.performanceInfo = performanceInfo;
        inf = (LayoutInflater) context.getSystemService
        (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return performanceInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return performanceInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);
        ImageView iv = (ImageView)convertView.findViewById(R.id.grid_item_image);
        Glide.with(convertView).load(performanceInfo.get(position).getThumbNail()).override(200,400).into(iv);
        return convertView;
    }
}
