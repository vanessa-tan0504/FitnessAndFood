package com.example.FitnessAndFood;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class DietVeganRVAdapter extends RecyclerView.Adapter<DietVeganRVAdapter.ViewHolder> {


    ArrayList<Integer> counter = new ArrayList<Integer>();
    ArrayList<Integer>iconList = new ArrayList<>();
    ArrayList<String> mealTitle= new ArrayList<>();
    ArrayList<String> mealList1= new ArrayList<>();
    ArrayList<String> mealList2= new ArrayList<>();

    Context context;

    public DietVeganRVAdapter(Context context,
                              ArrayList<Integer>iconList,
                              ArrayList<String> mealTitle,
                              ArrayList<String> mealList1,
                              ArrayList<String> mealList2) {

        this.iconList = iconList;
        this.mealTitle = mealTitle;
        this.mealList1 = mealList1;
        this.mealList2 = mealList2;
        this.context = context;


        for (int i = 0; i < mealTitle.size(); i++) {
            counter.add(0);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealtitle,mealtext1,mealtext2;
       // RecyclerView cardRecyclerView;
        CardView cardView;
        ImageView mealicon;

        public ViewHolder(View itemView) {
            super(itemView);
            mealtitle = itemView.findViewById(R.id.mealTitle);
            mealtext1 = itemView.findViewById(R.id.mealtext1);
            mealtext2 = itemView.findViewById(R.id.mealtext2);
            cardView = itemView.findViewById(R.id.mealcardView);
            mealicon = itemView.findViewById(R.id.mealicon);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diet_list, parent, false);

        DietVeganRVAdapter.ViewHolder vh = new DietVeganRVAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mealtitle.setText(mealTitle.get(position));
        holder.mealtext1.setText(mealList1.get(position));
        holder.mealtext2.setText(mealList2.get(position));
        holder.mealicon.setImageResource(iconList.get(position));
       // holder.icon.setImageResource(image.get(position));

//        //onclick to another activity for diet
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent;
//
//                switch(position){
//                    case 0:
//                        intent = new Intent(context,DietTabView.class);
//                        context.startActivity(intent);
//                        break;
////                    case 1:
////                        intent = new Intent(context,Workout1.class);
////                        context.startActivity(intent);
////                        break;
////                    case 2:
////                        intent = new Intent(context,Workout1.class);
////                        context.startActivity(intent);
////                        break;
////                    case 3:
////                        intent = new Intent(context,Workout1.class);
////                        context.startActivity(intent);
////                        break;
//
//                }
//
//                //counter.set(position, counter.get(position) + 1);
//
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mealTitle.size();
    }


}
