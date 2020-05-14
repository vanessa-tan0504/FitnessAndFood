package com.example.FitnessAndFood;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DietStandard7 extends Fragment {
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
       mealList1.add("~ 1 cookie (low-sugar or sugar-free)");
       mealList1.add("~ Omelet out of three eggs and some vegetables");
       mealList1.add("~ Vegetables salad (carrots, tomatoes, cucumber etc.)");

       mealList2.add("~ Cooked or boiled corn with vegetables");
       mealList2.add("");
       mealList2.add("~ Yogurt (low-fat or low-sugar)");
       mealList2.add("~ Any grilled or boiled lean meat (beef, chicken etc.)");

       DietStdRVAdapter dietstdRVAdapter =
               new DietStdRVAdapter(getActivity(),iconList,mealTitle,mealList1,mealList2);

       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       recyclerView.setAdapter(dietstdRVAdapter);

    }

}
