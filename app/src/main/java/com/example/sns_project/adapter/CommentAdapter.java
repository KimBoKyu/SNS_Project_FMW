package com.example.sns_project.adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sns_project.CommentInfo;
import com.example.sns_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class CommentAdapter  extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<CommentInfo> items = new ArrayList<CommentInfo>();
    private FirebaseUser User;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String postInfo_id;


    public CommentAdapter(Context context, int layout, ArrayList<CommentInfo> commentList, String postInfo_id) {
        this.context = context;
        this.layout = layout;
        this.items = commentList;
        this.postInfo_id = postInfo_id;
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
        ImageView comment_delete, user_photo;
    }



    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Drawable drawable = context.getResources().getDrawable(
                R.drawable.user);
        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtCreate_time = (TextView) row.findViewById(R.id.creat_time);
            holder.txtContent =(TextView) row.findViewById(R.id.comment);
            holder.txtUser_id = (TextView) row.findViewById(R.id.user_id);
            holder.comment_delete = (ImageView) row.findViewById(R.id.comment_delete);
            holder.user_photo = (ImageView) row.findViewById(R.id.profileImageView2) ;
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        final CommentInfo commentInfo = items.get(position);
        holder.txtCreate_time.setText(mFormat.format(commentInfo.getDate()));
        holder.txtContent.setText(commentInfo.getComment());
        holder.txtUser_id.setText(commentInfo.getUser());
        if(commentInfo.getPhotoUrl()!=null) {
            Glide.with(context).load(commentInfo.getPhotoUrl()).into(holder.user_photo);
        }
        else{
            Glide.with(context).load(drawable).into(holder.user_photo);
        }
        holder.comment_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User = FirebaseAuth.getInstance().getCurrentUser();
                if(User.getEmail().equals(commentInfo.getUser())){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // 제목셋팅
                    alertDialogBuilder.setTitle("댓글 삭제");

                    // AlertDialog 셋팅
                    alertDialogBuilder
                            .setMessage("댓글을 삭제하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("삭제",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            // 프로그램을 종료한다
                                            db.collection("posts").document(postInfo_id).collection("comments2").document(
                                                    items.get(position).getId()).delete();
                                            items.remove(position);
                                            notifyDataSetChanged();


                                        }
                                    })
                            .setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            // 다이얼로그를 취소한다
                                            dialog.cancel();
                                        }
                                    });

                    // 다이얼로그 생성
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // 다이얼로그 보여주기
                    alertDialog.show();
                }else{
                    Toast.makeText(context, "작성자가 아닙니다. 작성자 본인만 삭제가 가능한 댓글 입니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });


        return row;
    }
}
