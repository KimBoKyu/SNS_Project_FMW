package com.example.sns_project.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sns_project.PerformanceInfo;
import com.example.sns_project.R;

import java.util.ArrayList;


// 각 아이템을 위한 데이터를 담는 어댑터
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>  {

    private Context context;
    private ArrayList<PerformanceInfo> items = new ArrayList<>();
    private itemClickListener listener;

    public interface itemClickListener {
        void onItemClick(RecyclerViewHolder holder, View view, int position);
    }

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size(); // 어댑터에서 관리하는 아이템의 개수를 반환
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // recyclerview_item XML 레이아웃을 이용해 뷰 객체를 만든 후 뷰홀더에 담아 반환
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // 뷰홀더에 각 아이템의 데이터를 설정
        PerformanceInfo item = items.get(position);
        holder.setItem(item);
        holder.setOnItemClickListener(listener);
    }

    public void addItem(PerformanceInfo item) {
        items.add(item);    // 아이템 추가
    }

    public void addItems(ArrayList<PerformanceInfo> items) {
        this.items = items; // 아이템 배열 추가
    }

    public void replaceItems(ArrayList<PerformanceInfo> items){
        this.items = items;
    }

    public PerformanceInfo getItem(int position) {
        return items.get(position); // 아이템 가져오기
    }

    public void setOnItemClickListener(itemClickListener listener) {
        this.listener = listener;
    }
}


