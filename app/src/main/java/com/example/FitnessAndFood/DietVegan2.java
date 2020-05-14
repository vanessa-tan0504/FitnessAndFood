package com.example.FitnessAndFood;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DietVegan2 extends Fragment {

    RecyclerView recyclerView;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.diet_vegan,container,false);

        recyclerView = v.findViewById(R.id.rv_dietvegan);

        veganList();


        return v;
    }

    public void veganList(){

        ArrayList<Integer> iconList = new ArrayList<>();
        ArrayList<String> mealTitle= new ArrayList<>();
        ArrayList<String> mealList1= new ArrayList<>();
        ArrayList<String> mealList2= new ArrayList<>();

        iconList.add(R.drawable.breakfast);
        iconList.add(R.drawable.snacks);
        iconList.add(R.drawable.lunch);
        iconList.add(R.drawable.dinner);

        mealTitle.add("BREAKFAST");
        mealTitle.add("SNACKS");
        mealTitle.add("LUNCH");
        mealTitle.add("DINNER");

        mealList1.add("~ 1 cup of tea or black coffee without sugar or milk");
        mealList1.add("~ 1 cookie (low-sugar or sugar-free)");
        mealList1.add("~ Yogurt (low-fat or low-sugar)");
        mealList1.add("~ Any vegetable soup with no salt added");

        mealList2.add("~ Oatmeal with fat-free milk (oatmeal,cracked wheat or barley)");
        mealList2.add("~ Some pumpkin seeds or pecans.");
        mealList2.add("~ Cooked black bean pasta or chickpea pasta");
        mealList2.add("~ Omelet out of three eggs and some vegetables");

        DietVeganRVAdapter dietVeganRVAdapter =
                new DietVeganRVAdapter(getActivity(),iconList,mealTitle,mealList1,mealList2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(dietVeganRVAdapter);

    }


}
