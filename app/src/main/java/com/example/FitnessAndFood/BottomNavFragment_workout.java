package com.example.FitnessAndFood;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BottomNavFragment_workout extends Fragment {
    View v;
    ViewPager mViewPager;
    WorkoutRVAdapter workoutRVAdapter;
    ShadowTransformer mCardShadowTransformer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.bottom_fragment_workout,container,false);
        mViewPager = v.findViewById(R.id.viewPager);

        workoutRVAdapter = new WorkoutRVAdapter(getActivity());
        workoutRVAdapter.addWorkoutItem(new WorkoutItem(R.string.title_1, R.string.text_1, R.drawable.workout1));
        workoutRVAdapter.addWorkoutItem(new WorkoutItem(R.string.title_2, R.string.text_2, R.drawable.workout2));
        workoutRVAdapter.addWorkoutItem(new WorkoutItem(R.string.title_3, R.string.text_3, R.drawable.workout3));
        workoutRVAdapter.addWorkoutItem(new WorkoutItem(R.string.title_4, R.string.text_4, R.drawable.workout4));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, workoutRVAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(workoutRVAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        return v;
    }

}


