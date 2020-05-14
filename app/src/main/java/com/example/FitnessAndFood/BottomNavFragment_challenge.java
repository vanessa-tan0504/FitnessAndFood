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

import java.util.ArrayList;

public class BottomNavFragment_challenge extends Fragment {
    RecyclerView recyclerView,recyclerView2,recyclerView3;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.bottom_fragment_challenge,container,false);

        recyclerView = v.findViewById(R.id.rv_challenge);
        recyclerView2= v.findViewById(R.id.rv_challenge2);
        recyclerView3= v.findViewById(R.id.rv_challenge3);

        challengeList();
        return v;
    }

    public void challengeList(){

        ArrayList<String> challengeList = new ArrayList<>();
        ArrayList<Integer>imageList = new ArrayList<>();

        ArrayList<String> challengeList2 = new ArrayList<>();
        ArrayList<Integer>imageList2 = new ArrayList<>();

        ArrayList<String> challengeList3 = new ArrayList<>();
        ArrayList<Integer>imageList3 = new ArrayList<>();

        //title arraylist
        challengeList.add("Upper Body\nChallenge");
        challengeList.add("Lower Body\nChallenge");
        challengeList.add("Full Body\nChallenge");

        challengeList2.add("Upper Body\nChallenge");
        challengeList2.add("Lower Body\nChallenge");
        challengeList2.add("Full Body\nChallenge");

        challengeList3.add("Upper Body\nChallenge");
        challengeList3.add("Lower Body\nChallenge");
        challengeList3.add("Full Body\nChallenge");


        //image arraylist
        imageList.add(R.drawable.chlg1);
        imageList.add(R.drawable.chlg2);
        imageList.add(R.drawable.chlg3);

        imageList2.add(R.drawable.chlg4);
        imageList2.add(R.drawable.chlg5);
        imageList2.add(R.drawable.chlg6);

        imageList3.add(R.drawable.chlg7);
        imageList3.add(R.drawable.chlg8);
        imageList3.add(R.drawable.chlg9);


        //easy challenge adapter
        ChallengeRVAdapter challengeRVAdapter =
                new ChallengeRVAdapter(getActivity(),challengeList,imageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(challengeRVAdapter);


        Challenge2RVAdapter challenge2RVAdapter =
                new Challenge2RVAdapter(getActivity(),challengeList2,imageList2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setAdapter(challenge2RVAdapter);

        Challenge3RVAdapter challenge3RVAdapter =
                new Challenge3RVAdapter(getActivity(),challengeList3,imageList3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView3.setAdapter(challenge3RVAdapter);

    }


}
