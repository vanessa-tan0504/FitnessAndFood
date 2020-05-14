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


public class ChallengeRVAdapter extends RecyclerView.Adapter<ChallengeRVAdapter.ViewHolder> {

    ArrayList<String> challengeList = new ArrayList<String>();
    ArrayList<Integer> image = new ArrayList<Integer>();
    ArrayList<Integer> counter = new ArrayList<Integer>();
    Context context;

    public ChallengeRVAdapter(Context context,
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
        TextView title;
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

        ChallengeRVAdapter.ViewHolder vh = new ChallengeRVAdapter.ViewHolder(v);

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
                    case 0: //easy upper body challenge
                        intent = new Intent(context, ChallengeEasy1.class); //easy1
                        context.startActivity(intent);
                        break;
                    case 1://easy lower body challenge
                        intent = new Intent(context, ChallengeEasy2.class); //easy2
                        context.startActivity(intent);
                        break;
                    case 2://easy full body challenge
                        intent = new Intent(context, ChallengeEasy3.class); //easy3
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
