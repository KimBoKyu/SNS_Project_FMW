package com.example.sns_project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private EditText editText;
    private FirebaseUser User;
    private String msg;
    private ListView listView;
    ArrayList<CommentInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postInfo = (PostInfo) getIntent().getSerializableExtra("postInfo");
        contentsLayout = findViewById(R.id.contentsLayout);
        readContentsVIew = findViewById(R.id.readContentsView);

        editText = findViewById(R.id.editText);
        findViewById(R.id.button).setOnClickListener(onClickListener);

        firebaseHelper = new FirebaseHelper(this);
        firebaseHelper.setOnPostListener(onPostListener);

        listView = findViewById(R.id.listview);

        commentList();
        uiUpdate();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button:
                    commentUpdate();

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
                    showToast(this, "수정되었습니다.");
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
        User = FirebaseAuth.getInstance().getCurrentUser();
        msg = editText.getText().toString();
        Map<String, Object> user = new HashMap<>();
        user.put("comment", msg);
        user.put("user", User.getEmail().toString());
        user.put("createAt", new Date());
        db.collection("posts").document(postInfo.getId()).collection("comments").add(user);
        commentList();
    }

    private void commentList(){
        list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User = FirebaseAuth.getInstance().getCurrentUser();
        final CommentAdapter adapter = new CommentAdapter(this, R.layout.item_comment, list);

        db.collection("posts").document(postInfo.getId()).collection("comments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("바보", document.getId() + " => " + document.getData());
                                list.add(new CommentInfo(document.getData().get("comment").toString(), new Date(document.getDate("createAt").getTime()), document.getData().get("user").toString()));


                            }
                            adapter.notifyDataSetChanged();

                        } else {
                            Log.w("바보", "Error getting documents.", task.getException());
                            Log.d("바보", "바보");
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
