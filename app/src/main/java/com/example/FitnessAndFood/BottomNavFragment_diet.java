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

public class BottomNavFragment_diet extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view= inflater.inflate(R.layout.bottom_fragment_diet,container,false);

        recyclerView = view.findViewById(R.id.rv_diet);

        weekList();


        return view;

    }

    private void weekList() {

        ArrayList<String> colorList = new ArrayList<>();
        ArrayList<Integer>imageList = new ArrayList<>();

        //image arraylist
        imageList.add(R.drawable.sunday);
        imageList.add(R.drawable.monday);
        imageList.add(R.drawable.tuesday);
        imageList.add(R.drawable.wednesday);
        imageList.add(R.drawable.thursday);
        imageList.add(R.drawable.friday);
        imageList.add(R.drawable.saturday);


        DietRVAdapter dietRVAdapter =
                new DietRVAdapter(getActivity(),imageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(dietRVAdapter);


    }





}
