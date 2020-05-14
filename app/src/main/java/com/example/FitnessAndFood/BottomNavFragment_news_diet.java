package com.example.FitnessAndFood;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BottomNavFragment_news_diet extends Fragment {
    RecyclerView recyclerView;
    View v;
    ArrayList<String> newsDietList = new ArrayList<>();
    ArrayList<String>imageList = new ArrayList<>();
    ArrayList<String> descList = new ArrayList<>();
    ArrayList<String> urlList = new ArrayList<>();
    private FirebaseFirestore db;
    ProgressBar progressBarD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.bottom_fragment_news_diet,container,false);

        recyclerView = v.findViewById(R.id.rv_newsDiet);
        progressBarD = v.findViewById(R.id.progressBarD);
        db = FirebaseFirestore.getInstance();
        challengeList();
        return v;
    }

    public void challengeList(){

        // DATABASE CHG
        db.collection("DietNews").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        newsDietList.add(document.getString("title")); //title arraylist
                        imageList.add(document.getString("img"));  //image arraylist
                        descList.add(document.getString("desc"));  //description arraylist
                        urlList.add(document.getString("url")); //url arraylist
                    }
                }
                //CHG place
                progressBarD.setVisibility(View.GONE);
                NewsRVAdapter newsRVAdapter =
                        new NewsRVAdapter(getActivity(),newsDietList,imageList,descList,urlList);

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(newsRVAdapter);
            }
        });
    }
}

