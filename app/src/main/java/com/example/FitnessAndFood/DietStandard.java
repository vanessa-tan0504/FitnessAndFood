package com.example.FitnessAndFood;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DietStandard extends Fragment {
    //ExampleItem mExampleList;
    RecyclerView recyclerView;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.diet_standard,container,false);

        recyclerView = v.findViewById(R.id.rv_dietstd);

        standardList();


        return v;
    }

   public void standardList(){

       ArrayList<Integer>iconList = new ArrayList<>();
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
       mealList1.add("~ Any no-salted nuts");
       mealList1.add("~ 2 piece of whole grain toast with vegetables (onions,tomato etc.)");
       mealList1.add("~ Chicken salad (chiken breast,grilled onion with some fruits");

       mealList2.add("~ Oatmeal porridge");
       mealList2.add("~ 1 piece of cheese (salty or low-fat)");
       mealList2.add("~ Any grilled or boiled lean meat (beef,chicken etc).");
       mealList2.add("");

       DietStdRVAdapter dietstdRVAdapter =
               new DietStdRVAdapter(getActivity(),iconList,mealTitle,mealList1,mealList2);

       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       recyclerView.setAdapter(dietstdRVAdapter);

    }

}
