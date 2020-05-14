package com.example.FitnessAndFood;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;


public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {

    ArrayList<String> titleList = new ArrayList<String>();
    ArrayList<String> imageList = new ArrayList<String>();  //CHG
    ArrayList<Integer> counter = new ArrayList<Integer>();
    ArrayList<String> descList = new ArrayList<String>();
    ArrayList<String> urlList = new ArrayList<String>();
    Context context;
    ProgressBar progressBar;

    public NewsRVAdapter(Context context,
                         ArrayList<String> titleList,
                         ArrayList<String> imageList,  //CHG
                         ArrayList<String> descList,
                         ArrayList<String> urlList) {

        this.titleList = titleList;
        this.imageList = imageList;
        this.descList = descList;
        this.context = context;
        this.urlList=urlList;


        for (int i = 0; i < titleList.size(); i++) {
            counter.add(0);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc;
        Button readBtn,shareBtn;
        CardView cardView;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            readBtn = itemView.findViewById(R.id.readMore);
            shareBtn = itemView.findViewById(R.id.share);
            cardView = itemView.findViewById(R.id.news_cardView);
            image = itemView.findViewById(R.id.news_image);
            desc = itemView.findViewById(R.id.news_desc);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list, parent, false);

        NewsRVAdapter.ViewHolder vh = new NewsRVAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(titleList.get(position));
        Glide.with(context).load(imageList.get(position)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);  //CHG  progressbar added in news_list
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);  //CHG
                return false;
            }
        }).into(holder.image);  //CHG
        holder.desc.setText(descList.get(position));

    //onclick for read more button
        holder.readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Uri webpage;
                switch(position){
                    case 0:
                        webpage = Uri.parse(urlList.get(position));
                        intent = new Intent(Intent.ACTION_VIEW,webpage);
                        if(intent.resolveActivity(context.getPackageManager())!=null){
                            context.startActivity(intent);
                        }
                        else {
                            Log.d("ImplicitIntents","Can't handle this intent!");
                        }
                        break;
                    case 1:
                        webpage = Uri.parse(urlList.get(position));
                        intent = new Intent(Intent.ACTION_VIEW,webpage);
                        if(intent.resolveActivity(context.getPackageManager())!=null){
                            context.startActivity(intent);
                        }
                        else {
                            Log.d("ImplicitIntents","Can't handle this intent!");
                        }
                        break;
                    case 2:
                        webpage = Uri.parse(urlList.get(position));
                        intent = new Intent(Intent.ACTION_VIEW,webpage);
                        if(intent.resolveActivity(context.getPackageManager())!=null){
                            context.startActivity(intent);
                        }
                        else {
                            Log.d("ImplicitIntents","Can't handle this intent!");
                        }
                        break;
                    case 3:
                        webpage = Uri.parse(urlList.get(position));
                        intent = new Intent(Intent.ACTION_VIEW,webpage);
                        if(intent.resolveActivity(context.getPackageManager())!=null){
                            context.startActivity(intent);
                        }
                        else {
                            Log.d("ImplicitIntents","Can't handle this intent!");
                        }
                        break;
                }

            }
        });

        //onclick for share button
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent (Intent.ACTION_SEND);
               // Uri webpage;
                switch(position){
                    case 0:
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_SUBJECT, titleList.get(position) );
                        share.putExtra(Intent.EXTRA_TEXT,urlList.get(position));
                        context.startActivity(Intent.createChooser(share,"Share link with:"));
                        break;
                    case 1:
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_SUBJECT, titleList.get(position) );
                        share.putExtra(Intent.EXTRA_TEXT,urlList.get(position));
                        context.startActivity(Intent.createChooser(share,"Share link with:"));
                        break;
                    case 2:
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_SUBJECT, titleList.get(position) );
                        share.putExtra(Intent.EXTRA_TEXT,urlList.get(position));
                        context.startActivity(Intent.createChooser(share,"Share link with:"));
                        break;
                    case 3:
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_SUBJECT, titleList.get(position) );
                        share.putExtra(Intent.EXTRA_TEXT,urlList.get(position));
                        context.startActivity(Intent.createChooser(share,"Share link with:"));
                        break;

                }

            }
        });
   }

    @Override
    public int getItemCount() {

        return titleList.size();
    }

}
