package com.example.sns_project.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.sns_project.MlistInfo;
import com.example.sns_project.PostInfo;
import com.example.sns_project.R;
import com.example.sns_project.activity.LoginActivity;
import com.example.sns_project.activity.MemberModifyActivity;
import com.example.sns_project.activity.PostActivity;
import com.example.sns_project.adapter.MlistAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class UserInfoFragment extends Fragment {


    private ListView mListView;
    private ArrayList<String> mList = new ArrayList();
    private ArrayList<MlistInfo> list;
    //private ArrayAdapter mAdapter;



    private static final String TAG = "UserInfoFragment";
    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_user_info) ;




    }



    @Override
    public void onResume(){
        super.onResume();
        updateUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        final ImageView profileImageView = view.findViewById(R.id.profileImageView);
        final TextView nameTextView = view.findViewById(R.id.nameTextView);
        final TextView phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        final TextView birthDayTextView = view.findViewById(R.id.birthDayTextView);
        final Button logoutButton = view.findViewById(R.id.button_signOut);
        logoutButton.setOnClickListener(onClickListener);
        final Button modifyButton = view.findViewById(R.id.button_modify);
        modifyButton.setOnClickListener(onClickListener);
        mListView = view.findViewById(R.id.myTitleListView) ;
        list = new ArrayList<>();

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            if(document.getData().get("photoUrl") != null){
                                Glide.with(getActivity()).load(document.getData().get("photoUrl")).centerCrop().override(500).into(profileImageView);
                            }
                            nameTextView.setText(document.getData().get("name").toString());
                            phoneNumberTextView.setText(document.getData().get("phoneNumber").toString());
                            birthDayTextView.setText(document.getData().get("birthDay").toString());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        final MlistAdapter adapter = new MlistAdapter(getActivity(), R.layout.item_mlist, list);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final CollectionReference posts = FirebaseFirestore.getInstance().collection("posts");

        posts.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //int i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(mAuth.getUid().equals(document.getData().get("publisher"))){
                            list.add(new MlistInfo(document.getData().get("title").toString(), new Date(document.getDate("createdAt").getTime())));
                            adapter.notifyDataSetChanged();
                            //System.out.println(mList.get(i++));

                        }
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                final MlistInfo item = (MlistInfo) adapter.getItem(position);
                Log.d("멍청이", "멍청이");
                //CollectionReference posts = FirebaseFirestore.getInstance().collection("posts");
                posts.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(item.getTitle().equals(document.getData().get("title"))&& item.getDate().compareTo(new Date(document.getDate("createdAt").getTime()))==0){
                                    PostInfo postInfo =new PostInfo(
                                            document.getData().get("title").toString(),
                                            (ArrayList<String>) document.getData().get("contents"),
                                            (ArrayList<String>) document.getData().get("formats"),
                                            document.getData().get("publisher").toString(),
                                            new Date(document.getDate("createdAt").getTime()),
                                            document.getId());
                                    Intent intent = new Intent(getActivity(), PostActivity.class);
                                    intent.putExtra("postInfo", postInfo);
                                    startActivity(intent);

                                }else{
                                    Log.d("강성수","강성수");
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

            }
        }) ;
        //mAdapter.notifyDataSetChanged();

        return view;
    }



    public void updateUser(){


        final ImageView profileImageView = getView().findViewById(R.id.profileImageView);
        final TextView nameTextView = getView().findViewById(R.id.nameTextView);
        final TextView phoneNumberTextView = getView().findViewById(R.id.phoneNumberTextView);
        final TextView birthDayTextView = getView().findViewById(R.id.birthDayTextView);
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            if(document.getData().get("photoUrl") != null){
                                Glide.with(getActivity()).load(document.getData().get("photoUrl")).centerCrop().override(500).into(profileImageView);
                            }
                            nameTextView.setText(document.getData().get("name").toString());
                            phoneNumberTextView.setText(document.getData().get("phoneNumber").toString());
                            birthDayTextView.setText(document.getData().get("birthDay").toString());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_signOut:
                    logout();
                    myStartActivity(LoginActivity.class);
                    break;

                case R.id.button_modify:
                    myStartActivity(MemberModifyActivity.class);
                    break;
            }
        }
    };




    public void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

}