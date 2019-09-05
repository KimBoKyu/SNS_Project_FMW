package com.example.sns_project.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sns_project.PerformanceInfo;
import com.example.sns_project.R;

/*
 * 화면에 보일 때 사용되는 각각의 뷰는 뷰홀더에 담아두게 된다.
 * 뷰홀더 객체가 생성될 때 뷰가 전달되는데, 뷰홀더 객체는 뷰를 담아두고 뷰에 표시될 데이터를 설정한다.
 * 뷰홀더는 재사용된다.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView textTitle;
    private TextView textLocation;
    private TextView textDistance;
    private ImageView imgPerformance;
    private RecyclerViewAdapter.itemClickListener listener;

    RecyclerViewHolder(View itemView) {
        super(itemView);

        textTitle = itemView.findViewById(R.id.textTitle);
        textLocation = itemView.findViewById(R.id.textLocation);
        textDistance = itemView.findViewById(R.id.textDistance);
        imgPerformance = itemView.findViewById(R.id.imgPerformance);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(RecyclerViewHolder.this, view, position);
                }
            }
        });
    }

    // PersonItem 객체를 전달받아 뷰홀더 안에 있는 뷰에 데이터 설정
    void setItem(PerformanceInfo item) {
        textTitle.setText(item.getTitle());
        textLocation.setText(item.getArea());
        textDistance.setText(item.getDistance());
        Glide.with(itemView).load(item.getThumbNail()).into(imgPerformance);
    }

    void setOnItemClickListener(RecyclerViewAdapter.itemClickListener listener) {
        this.listener = listener;
    }
}

