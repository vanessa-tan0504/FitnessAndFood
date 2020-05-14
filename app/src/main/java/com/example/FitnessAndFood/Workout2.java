package com.example.FitnessAndFood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class Workout2 extends AppCompatActivity {
    RecyclerView recyclerView;
    Button start;
    ImageView img;
    TextView title,desc;
    final static int START_WORKOUT2=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.rv_subworkout1);
        start= (Button)findViewById(R.id.start);
        img = findViewById(R.id.sub_image);

        img.setImageResource(R.drawable.workout5);
        title=findViewById(R.id.sub_title);
        title.setText("BUTT WORKOUT");
        desc=findViewById(R.id.sub_desc);
        desc.setText("46 kcal\t\t 4-6 mins\t\t 4 workouts");

        challengeList();


    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //for back button
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


   //subworkout for recycler view
    public void challengeList(){

        ArrayList<String> sub1List = new ArrayList<>();
        ArrayList<Integer>gifList = new ArrayList<>();
        ArrayList<String> descList = new ArrayList<>();
        ArrayList<String> extraDescList = new ArrayList<>();

        //title arraylist
        sub1List.add("Squats");
        sub1List.add("Forward Lunges");
        sub1List.add("Legs Raise");
        sub1List.add("Wall Sit");

        //image arraylist
        gifList.add(R.drawable.squats);
        gifList.add(R.drawable.forwardlunges);
        gifList.add(R.drawable.legraise);
        gifList.add(R.drawable.wallsit);


        //desc arraylist
        descList.add("x24");
        descList.add("x10");
        descList.add("x10");
        descList.add("00:12");

        //extra desc arrayList
        extraDescList.add(getString(R.string.workout2_1));
        extraDescList.add(getString(R.string.workout2_2));
        extraDescList.add(getString(R.string.workout2_3));
        extraDescList.add(getString(R.string.workout2_4));

        //adapter for workout1, pass arraylist above to adapter
        InnerWorkoutRVAdapter innerWorkoutRVAdapter =
                new InnerWorkoutRVAdapter(this,sub1List,gifList,descList,extraDescList);

        //set recyclerview layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(innerWorkoutRVAdapter);
    }

    //when user press start workout,
    public void onClick_start(View view) {
        Intent intent = new Intent(this, Startworkout2.class);
        startActivityForResult(intent,START_WORKOUT2);


    }
}
