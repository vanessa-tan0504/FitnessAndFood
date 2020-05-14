package com.example.FitnessAndFood;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class DietRVAdapter extends RecyclerView.Adapter<DietRVAdapter.ViewHolder> {

    ArrayList<Integer> image = new ArrayList<Integer>();
    ArrayList<Integer> counter = new ArrayList<Integer>();

    Context context;

    public DietRVAdapter(Context context,
                         ArrayList<Integer> image) {
        this.image = image;
        this.context = context;



        for (int i = 0; i < image.size(); i++) {
            counter.add(0);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.weekLayoutHolder);
            cardView = itemView.findViewById(R.id.weekcardView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diet_weeklist, parent, false);

        DietRVAdapter.ViewHolder vh = new DietRVAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.constraintLayout.setBackgroundResource(image.get(position));

        //onclick to another activity for diet
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                switch(position){
                    case 0:
                        intent = new Intent(context, DietTabView.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context, DietTabView2.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(context, DietTabView3.class);
                        context.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(context, DietTabView4.class);
                        context.startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(context, DietTabView5.class);
                        context.startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(context, DietTabView6.class);
                        context.startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(context, DietTabView7.class);
                        context.startActivity(intent);
                        break;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return image.size();
    }


}
