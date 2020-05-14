package com.example.FitnessAndFood;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class ProfilePrepare extends AppCompatActivity {
    private TextView height,weight,gender,goals,descHeight,descWeight,descGender,descGoal;
    private Button start;
    private int click1=0,click2=0,click3=0,click4=0;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private int heightValue,weightValue,genderValue,goalValue;
    private double BMI;
    private Date current;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_prepare);

        height=findViewById(R.id.height);
        weight=findViewById(R.id.weight);
        gender=findViewById(R.id.gender);
        goals=findViewById(R.id.goal);
        descHeight=findViewById(R.id.height_desc);
        descWeight=findViewById(R.id.weight_desc);
        descGender=findViewById(R.id.gender_desc);
        descGoal=findViewById(R.id.goal_desc);
        start = findViewById(R.id.prepare_start);

        //CHG
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        current = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        date = dateFormat.format(current);

        //button start listener
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                if(click1<=0||click2<=0||click3<=0||click4<=0){
                    Toast.makeText(ProfilePrepare.this, "Please fill in all field", Toast.LENGTH_SHORT).show();
                }
                else {
                    BMI = weightValue/Math.pow(heightValue/100.00,2);
                    Map<String, Object> user = new HashMap<>();
                    user.put("height", Integer.toString(heightValue));
                    user.put("weight", Integer.toString(weightValue));
                    user.put("history1", Integer.toString(weightValue));
                    user.put("history2", "");
                    user.put("history3", "");
                    user.put("history4", "");
                    user.put("history5", "");
                    user.put("counter", "1");
                    user.put("lightest", Integer.toString(weightValue));
                    user.put("heaviest", Integer.toString(weightValue));
                    user.put("bmi", String.format("%.2f",BMI));
                    if(genderValue<1)
                        user.put("gender", "Male");
                    else
                        user.put("gender","Female");
                    switch(goalValue){
                        case 0: user.put("goal","Lose Weight");
                            break;
                        case 1: user.put("goal","Relieve Stress");
                            break;
                        case 2: user.put("goal","Gain Strength");
                            break;
                        case 3: user.put("goal","Increase Endurance");
                            break;
                        case 4: user.put("goal","Improve Fitness");
                            break;
                    }
                    // CHG Add n update details onto Firestore document
                    db.collection("Users").document(currentUser.getEmail()).set(user, SetOptions.mergeFields("height","weight","history1","history2","history3","history4","history5","counter","lightest","heaviest","bmi","gender","goal")).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfilePrepare.this, "Details Received!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(v.getContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });


        //height listener
        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click1++;
                heightValue = showHeightDialog();
            }
        });

        //weight listener
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click2++;
                weightValue = showWeightDialog();
            }
        });

        //gender listener
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click3++;
                genderValue = showGenderDialog();
            }
        });

        //goals listener
        goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click4++;
                goalValue = showGoalsDialog();
            }
        });

    }

    // for height dialog box
    private int showHeightDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.prepare_dialogbox);

        TextView tv_title = (TextView)dialog.findViewById(R.id.dialog_prepareTitle);
        final NumberPicker np = (NumberPicker)dialog.findViewById(R.id.dialog_preparePicker);
        tv_title.setText("Pick Your Height (cm)");
        np.setMinValue(140);
        np.setMaxValue(210);

        Button btnokay=(Button)dialog.findViewById(R.id.buttonOk);

        btnokay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int picked = np.getValue();
                heightValue = picked;
                descHeight.setText(Integer.toString(picked));
                dialog.cancel();
            }
        });
        dialog.show();

        //dialogbox size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        int dialogWindowHeight = (int) (displayHeight * 0.5f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        dialog.getWindow().setAttributes(layoutParams);
        return heightValue;
    }

    // for weight dialog box
    private int showWeightDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.prepare_dialogbox);

        TextView tv_title = (TextView)dialog.findViewById(R.id.dialog_prepareTitle);
        final NumberPicker np = (NumberPicker)dialog.findViewById(R.id.dialog_preparePicker);
        tv_title.setText("Pick Your Weight (kg)");

        np.setWrapSelectorWheel(true); //wheel wraps when reach min/max value
        np.setMinValue(40);
        np.setMaxValue(200);

        Button btnokay=(Button)dialog.findViewById(R.id.buttonOk);

        btnokay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int picked = np.getValue();
                weightValue = picked;
                descWeight.setText(Integer.toString(picked));
                dialog.cancel();
            }
        });
        dialog.show();

        //dialogbox size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        int dialogWindowHeight = (int) (displayHeight * 0.5f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        dialog.getWindow().setAttributes(layoutParams);
        return weightValue;
    }

    // for gender dialog box
    private int showGenderDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.prepare_dialogbox);

        TextView tv_title = (TextView)dialog.findViewById(R.id.dialog_prepareTitle);
        final NumberPicker np = (NumberPicker)dialog.findViewById(R.id.dialog_preparePicker);
        tv_title.setText("Pick Your Gender");
        np.setMinValue(0);
        np.setMaxValue(1);
        np.setDisplayedValues(new String[]{"Male","Female"});

        Button btnokay=(Button)dialog.findViewById(R.id.buttonOk);
        btnokay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int picked;
                picked = np.getValue();
                genderValue = picked;
                switch(picked){
                    case 0: descGender.setText("Male");
                    break;
                    case 1: descGender.setText("Female");
                        break;
                }
                dialog.cancel();

            }
        });
        dialog.show();

        //dialogbox size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        int dialogWindowHeight = (int) (displayHeight * 0.5f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        dialog.getWindow().setAttributes(layoutParams);

        return genderValue;
    }

    // for goals dialog box
    private int showGoalsDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.prepare_dialogbox);

        TextView tv_title = (TextView)dialog.findViewById(R.id.dialog_prepareTitle);
        final NumberPicker np = (NumberPicker)dialog.findViewById(R.id.dialog_preparePicker);
        tv_title.setText("Pick Your Goals");
        np.setMinValue(0);
        np.setMaxValue(4);
        np.setDisplayedValues(new String[]{"Lose Weight","Relieve Stress","Gain Strength","Increase Endurance","Improve Fitness"});

        Button btnokay=(Button)dialog.findViewById(R.id.buttonOk);

        btnokay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int picked = np.getValue();
                goalValue = picked;
                switch(picked){
                    case 0: descGoal.setText("Lose Weight");
                        break;
                    case 1: descGoal.setText("Relieve Stress");
                        break;
                    case 2: descGoal.setText("Gain Strength");
                        break;
                    case 3: descGoal.setText("Increase Endurance");
                        break;
                    case 4: descGoal.setText("Improve Fitness");
                        break;
                }
                dialog.cancel();
            }
        });
        dialog.show();

        //dialogbox size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        int dialogWindowHeight = (int) (displayHeight * 0.5f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        dialog.getWindow().setAttributes(layoutParams);

        return goalValue;
    }
}
