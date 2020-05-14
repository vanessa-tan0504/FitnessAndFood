package com.example.FitnessAndFood;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class WorkoutRVAdapter extends PagerAdapter implements CardAdapter {

    private final Context context;
    private List<CardView> mViews;
    private List<WorkoutItem> mData;
    private float mBaseElevation;

    public WorkoutRVAdapter(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.context = context;
    }

    public void addWorkoutItem(WorkoutItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.workout_list, container, false);
        container.addView(view);
        view.setTag(position);
        bind(mData.get(position), view, position);
        final CardView cardView = view.findViewById(R.id.cardView);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(WorkoutItem item, View view, final int position) {
        TextView titleTextView = view.findViewById(R.id.title);
        TextView contentTextView = view.findViewById(R.id.content);
        Button btn = view.findViewById(R.id.startWorkout);
        FrameLayout bg = view.findViewById(R.id.workout_fl);

        bg.setBackgroundResource(item.getBg());
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch(position){
                    case 0:
                        intent = new Intent(context, Workout1.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context, Workout2.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(context, Workout3.class);
                        context.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(context, Workout4.class);
                        context.startActivity(intent);
                        break;
                }

            }
        });
    }


}

