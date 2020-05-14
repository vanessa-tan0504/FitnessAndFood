package com.example.FitnessAndFood;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Challenge3RVAdapter extends RecyclerView.Adapter<Challenge3RVAdapter.ViewHolder> {

    ArrayList<String> challengeList = new ArrayList<String>();
    ArrayList<Integer> image = new ArrayList<Integer>();
    ArrayList<Integer> counter = new ArrayList<Integer>();
    Context context;

    public Challenge3RVAdapter(Context context,
                               ArrayList<String> challengeList,
                               ArrayList<Integer> image) {
        this.challengeList = challengeList;
        this.image = image;
        this.context = context;


        for (int i = 0; i < challengeList.size(); i++) {
            counter.add(0);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc;
        //ImageButton dropBtn;
        CardView cardView;
        LinearLayout bg;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.challenge_title);
            //dropBtn = itemView.findViewById(R.id.goto_arrow);
            cardView = itemView.findViewById(R.id.challenge_cardView);
            bg = itemView.findViewById(R.id.challenge_linearLayoutHolder);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.challenge_list, parent, false);

        Challenge3RVAdapter.ViewHolder vh = new Challenge3RVAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(challengeList.get(position));
        holder.bg.setBackgroundResource(image.get(position));


    //onclick to another  activity for challenge
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch(position){
                    case 0:
                        intent = new Intent(context, ChallengeAdv1.class); //advance1
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context, ChallengeAdv2.class); //advance2
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(context, ChallengeAdv3.class); //advance3
                        context.startActivity(intent);
                        break;

                }

            }
        });
   }

    @Override
    public int getItemCount() {

        return challengeList.size();
    }

}
