package com.example.sns_project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sns_project.CommentInfo;
import com.example.sns_project.R;

import org.w3c.dom.Comment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CommentAdapter  extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<CommentInfo> items = new ArrayList<CommentInfo>();

    public CommentAdapter(Context context, int layout, ArrayList<CommentInfo> commentList) {
        this.context = context;
        this.layout = layout;
        this.items = commentList;
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
        TextView txtCreate_time, txtContent, txtUser_id;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtCreate_time = (TextView) row.findViewById(R.id.creat_time);
            holder.txtContent =(TextView) row.findViewById(R.id.comment);
            holder.txtUser_id = (TextView) row.findViewById(R.id.user_id);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        CommentInfo commentInfo = items.get(position);
        holder.txtCreate_time.setText(commentInfo.getDate().toString());
        holder.txtContent.setText(commentInfo.getComment());
        holder.txtUser_id.setText(commentInfo.getUser());

        return row;
    }
}
