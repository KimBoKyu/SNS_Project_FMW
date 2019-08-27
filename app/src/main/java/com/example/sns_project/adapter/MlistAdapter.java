package com.example.sns_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sns_project.MlistInfo;
import com.example.sns_project.R;

import java.util.ArrayList;

public class MlistAdapter  extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<MlistInfo> items = new ArrayList<MlistInfo>();

    public MlistAdapter(Context context, int layout, ArrayList<MlistInfo> mList) {
        this.context = context;
        this.layout = layout;
        this.items = mList;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtCreate_time, txtContent;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtCreate_time = (TextView) row.findViewById(R.id.createAt);
            holder.txtContent =(TextView) row.findViewById(R.id.mtitle);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        MlistInfo mlistInfo = items.get(position);
        holder.txtCreate_time.setText(mlistInfo.getDate().toString());
        holder.txtContent.setText(mlistInfo.getTitle());

        return row;
    }
}
