package com.example.sns_project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.sns_project.CommentInfo;
import com.example.sns_project.FirebaseHelper;
import com.example.sns_project.PostInfo;
import com.example.sns_project.R;
import com.example.sns_project.adapter.CommentAdapter;
import com.example.sns_project.listener.OnPostListener;
import com.example.sns_project.view.ReadContentsVIew;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.sns_project.Util.showToast;

public class PostActivity extends BasicActivity {
    private PostInfo postInfo;
    private FirebaseHelper firebaseHelper;
    private ReadContentsVIew readContentsVIew;
    private LinearLayout contentsLayout;
    private EditText commentEditText;
    private FirebaseUser User;
    private String msg;
    private ListView listView;
    private ArrayList<CommentInfo> list;
    private String[] photoUrl = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        postInfo = (PostInfo) getIntent().getSerializableExtra("postInfo");
        listView = findViewById(R.id.listview);
        commentList();
        contentsLayout = findViewById(R.id.contentsLayout);
        readContentsVIew = findViewById(R.id.readContentsView);
        commentEditText = findViewById(R.id.commentEditText);
        findViewById(R.id.commentbutton).setOnClickListener(onClickListener);
        firebaseHelper = new FirebaseHelper(this);
        firebaseHelper.setOnPostListener(onPostListener);
        uiUpdate();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.commentbutton:
                    commentUpdate();
                    commentEditText.setText("");
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    postInfo = (PostInfo)data.getSerializableExtra("postinfo");
                    contentsLayout.removeAllViews();
                    uiUpdate();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()) {
            case R.id.delete:
                if(firebaseAuth.getUid().equals(postInfo.getPublisher())){
                    firebaseHelper.storageDelete(postInfo);
                    finish();
                    showToast(this, "삭제하였습니다.");
                }
                else{
                    showToast(this, "글쓴이가 다릅니다.");
                }
                return true;
            case R.id.modify:
                if(firebaseAuth.getUid().equals(postInfo.getPublisher())){
                    myStartActivity(WritePostActivity.class, postInfo);
                    finish();
                }
                else{
                    showToast(this, "글쓴이가 다릅니다.");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete(PostInfo postInfo) {
            Log.e("로그 ","삭제 성공");
        }

        @Override
        public void onModify() {
            Log.e("로그 ","수정 성공");
        }
    };

    private void uiUpdate(){
        setToolbarTitle(postInfo.getTitle());
        readContentsVIew.setPostInfo(postInfo);
    }

    private void commentUpdate(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseFirestore db2= FirebaseFirestore.getInstance();
        User = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String temp = "";
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        if(document.getId().equals(User.getUid())){
                            if(document.getData().get("photoUrl")!=null){
                                photoUrl[1] = new String(document.getData().get("photoUrl").toString());
                                msg = commentEditText.getText().toString();
                                Map<String, Object> user = new HashMap<>();
                                user.put("comment", msg);
                                user.put("user", User.getEmail().toString());
                                user.put("publisher", User.getUid());
                                user.put("createAt", new Date());
                                user.put("photoUrl", photoUrl[1]);
                                db2.collection("posts").document(postInfo.getId()).collection("comments2").add(user);
                                commentList();
                            }
                            else{
                                msg = commentEditText.getText().toString();
                                Map<String, Object> user = new HashMap<>();
                                user.put("comment", msg);
                                user.put("user", User.getEmail().toString());
                                user.put("publisher", User.getUid());
                                user.put("createAt", new Date());
                                user.put("photoUrl", null);

                                db2.collection("posts").document(postInfo.getId()).collection("comments2").add(user);
                                commentList();
                            }
                        }
                    }
                }
                else{
                }
            }
        });

    }

    private void commentList(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("posts").document(postInfo.getId()).collection("comments2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userPhotourl(document.getData().get("publisher").toString(), document.getId(),document.getData().get("comment").toString(), new Date(document.getDate("createAt").getTime()),
                                        document.getData().get("user").toString() );
                                }

                        } else {
                            Log.w("바보", "Error getting documents.", task.getException());
                        }

                    }
                });

    }

    private void userPhotourl(final String user_id, final String comments_id, final String comment, final Date createAt, final String user){
        list = new ArrayList<>();
        final CommentAdapter adapter = new CommentAdapter(this, R.layout.item_comment, list, postInfo.getId());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseFirestore db2 = FirebaseFirestore.getInstance();
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        if(document.getId().equals(user_id) && document.getData().get("photoUrl")!=null){
                            photoUrl[0] = document.getData().get("photoUrl").toString();
                            db2.collection("posts").document(postInfo.getId()).collection("comments2").document(comments_id).update("photoUrl",photoUrl[0]);
                            CommentInfo commentInfo = new CommentInfo(comment, createAt, user, photoUrl[0], comments_id);
                            list.add(commentInfo);
                            adapter.notifyDataSetChanged();
                        }else if(document.getId().equals(user_id) && document.getData().get("photoUrl")==null){
                            db2.collection("posts").document(postInfo.getId()).collection("comments2").document(comments_id).update("photoUrl",null);
                            CommentInfo commentInfo = new CommentInfo(comment, createAt, user, null,comments_id);
                            list.add(commentInfo);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    if (list.size() != 0) {
                        for (int i = 0; i < list.size() - 1; i++) {
                            for (int j = i + 1; j < list.size(); j++) {
                                if (list.get(i).getDate().compareTo(list.get(j).getDate()) > 0) {
                                    CommentInfo temp = list.get(i);
                                    list.set(i, list.get(j));
                                    list.set(j, temp);
                                }

                            }
                        }
                    }
                    ViewGroup.LayoutParams params = listView.getLayoutParams();
                    params.height = list.size()*140;
                    listView.setLayoutParams(params);
                    listView.requestLayout();
                }
                else{
                    Log.w("바보", "Error getting documents.", task.getException());
                }
            }
        });
        listView.setAdapter(adapter);
    }

    private void myStartActivity(Class c, PostInfo postInfo) {
        Intent intent = new Intent(this, c);
        intent.putExtra("postInfo", postInfo);
        startActivityForResult(intent, 0);
    }
}
