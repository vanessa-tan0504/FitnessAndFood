package com.example.FitnessAndFood;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;


public class InnerWorkoutRVAdapter extends RecyclerView.Adapter<InnerWorkoutRVAdapter.ViewHolder> {

    ArrayList<String> sub1List = new ArrayList<String>();
    ArrayList<Integer> giflist = new ArrayList<Integer>();
    ArrayList<Integer> counter = new ArrayList<Integer>();
    ArrayList<String> descList = new ArrayList<String>();
    ArrayList<String> extraDescList = new ArrayList<String>();
    Context context;

    public InnerWorkoutRVAdapter(Context context,
                                 ArrayList<String> sub1List,
                                 ArrayList<Integer> giflist,
                                 ArrayList<String> descList,
                                 ArrayList<String> extraDescList) {
        this.sub1List = sub1List;
        this.giflist = giflist;
        this.descList = descList;
        this.context = context;
        this.extraDescList=extraDescList;


        for (int i = 0; i < sub1List.size(); i++) {
            counter.add(0);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;
        GifImageView gifDrawable;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.sub1_title);
            cardView = itemView.findViewById(R.id.sub1_cardView);
            gifDrawable = itemView.findViewById(R.id.sub1_image);
            desc = itemView.findViewById(R.id.sub1_desc);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.innerworkout1_list, parent, false);

        InnerWorkoutRVAdapter.ViewHolder vh = new InnerWorkoutRVAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(sub1List.get(position));
        holder.desc.setText(descList.get(position));
        holder.gifDrawable.setImageResource(giflist.get(position));


        //onclick for additional description dialog box
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (position) {
                    case 0:
                        showCustomDialog(position);
                        break;
                    case 1:
                        showCustomDialog(position);
                        break;
                    case 2:
                        showCustomDialog(position);
                        break;
                    case 3:
                        showCustomDialog(position);
                        break;
                    case 4:
                        showCustomDialog(position);
                        break;
                    case 5:
                        showCustomDialog(position);
                        break;
                    case 6:
                        showCustomDialog(position);
                        break;
                    case 7:
                        showCustomDialog(position);
                        break;
                    case 8:
                        showCustomDialog(position);
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {

        return sub1List.size();
    }

    // for dialog box
    private void showCustomDialog(int position){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.description_dialogbox);

        GifImageView gif = (GifImageView)dialog.findViewById(R.id.dialog_gif) ;
        TextView title = (TextView)dialog.findViewById(R.id.dialog_title);
        TextView desc = (TextView)dialog.findViewById(R.id.dialog_desc);
        gif.setImageResource(giflist.get(position));
        title.setText(sub1List.get(position));
        desc.setText(extraDescList.get(position));

        Button btn =(Button)dialog.findViewById(R.id.buttonOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        //dialogbox resizing
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        int dialogWindowHeight = (int) (displayHeight * 0.8f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        dialog.getWindow().setAttributes(layoutParams);
    }


}

