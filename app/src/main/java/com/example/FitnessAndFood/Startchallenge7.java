package com.example.FitnessAndFood;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class Startchallenge7 extends AppCompatActivity {
   // private static final long START_TIME_IN_MILLIS = 21000;

    private TextView countDown,title,repeat,congratsText,congratsDesc,starting;
    private Button btnStartPause;
    private Button btnNext;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private int count=-1;
    private GifImageView gif;
    private Vibrator vibrate;
    ArrayList<String> sub1List = new ArrayList<String>();
    ArrayList<Integer>gifList = new ArrayList<>();
    private Chronometer countup; //ADDED
    private long pauseOffset; //ADDED
    private boolean running; //ADDED
    private ImageView finish; //Added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startworkout);

        title=findViewById(R.id.title);
        countDown = findViewById(R.id.text_view_countdown);
        starting=findViewById(R.id.startingtime);
        repeat = findViewById(R.id.text_view_repeat);
        btnStartPause= findViewById(R.id.button_start_pause);
        btnNext = findViewById(R.id.button_next);
        gif=findViewById(R.id.gif);
        congratsDesc=findViewById(R.id.congrats_desc);
        congratsText=findViewById(R.id.congrats_text);

        countDown.setVisibility(View.INVISIBLE);
        btnStartPause.setVisibility(View.INVISIBLE);

        congratsText.setVisibility(View.INVISIBLE);
        congratsDesc.setVisibility(View.INVISIBLE);

        vibrate = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        //show first workout title and gif
        repeat.setText("Jumping Jacks");
        gif.setImageResource(R.drawable.jumpingjacks);

        beginTimer(6000); //only 5 sec prestart timer
        updateBeginTimer();

        //ADDED
        countup = findViewById(R.id.countuptimer);
        countup.setFormat("\nTime taken to complete:\n %s");
        countup.setBase(SystemClock.elapsedRealtime());
        //ADDED

        //added
        finish = findViewById(R.id.finishlayout);
        finish.setVisibility(View.INVISIBLE);
        //added

        btnNext.setVisibility(View.INVISIBLE); //next button disappear first

        challengeList();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    count++;

                    //rest steps
                    if(count%2!=0 && count<17){
                        if(count!=1){
                            stopTimer();
                            resetTimer(21000); //rest 20secs
                        }
                        startTimer(21000);
                        updateCountDownText();
                        gif.setImageResource(R.drawable.rest);
                        title.setText("REST");
                        repeat.setVisibility(View.INVISIBLE);
                        countDown.setVisibility(View.VISIBLE);
                        btnStartPause.setVisibility(View.INVISIBLE);
                    }
                    else {
                        switch (count) {
                            case 0: //workout1
                                startChronometer(); //ADDED
                                stopTimer();
                                title.setText(sub1List.get(0));
                                stopTimer();
                                resetTimer(41000); //40 sec
                                startTimer(41000);
                                updateCountDownText();
                                gif.setImageResource(gifList.get(0));
                                gif.setVisibility(View.VISIBLE);
                                btnStartPause.setVisibility(View.VISIBLE);
                                countDown.setVisibility(View.VISIBLE);
                                repeat.setVisibility(View.INVISIBLE);
                                btnNext.setVisibility(View.INVISIBLE); //cannot click next until times up (for timing workout)

                                starting.setVisibility(View.INVISIBLE); //hide starting timer
                                break;

                            case 2: //workout2
                                stopTimer();
                                title.setText(sub1List.get(1));
                                repeat.setText("24 times");
                                gif.setImageResource(gifList.get(1));
                                gif.setVisibility(View.VISIBLE);
                                countDown.setVisibility(View.INVISIBLE);
                                repeat.setVisibility(View.VISIBLE);
                                break;

                            case 4://workout3
                                stopTimer();
                                title.setText(sub1List.get(2));
                                repeat.setText("22 times");
                                gif.setImageResource(gifList.get(2));
                                gif.setVisibility(View.VISIBLE);
                                countDown.setVisibility(View.INVISIBLE);
                                repeat.setVisibility(View.VISIBLE);
                                break;

                            case 6://workout4
                                title.setText(sub1List.get(3));
                                stopTimer();
                                resetTimer(21000); //20 sec
                                startTimer(21000);
                                updateCountDownText();
                                gif.setImageResource(gifList.get(3));
                                gif.setVisibility(View.VISIBLE);
                                btnStartPause.setVisibility(View.VISIBLE);
                                countDown.setVisibility(View.VISIBLE);
                                repeat.setVisibility(View.INVISIBLE);
                                btnNext.setVisibility(View.INVISIBLE); //cannot click next until times up (for timing workout)
                                break;

                            case 8://workout 5
                                stopTimer();
                                title.setText(sub1List.get(4));
                                repeat.setText("24 times");
                                gif.setImageResource(gifList.get(4));
                                gif.setVisibility(View.VISIBLE);
                                countDown.setVisibility(View.INVISIBLE);
                                repeat.setVisibility(View.VISIBLE);
                                break;

                            case 10://workout 6
                                title.setText(sub1List.get(5));
                                stopTimer();
                                resetTimer(21000); //20 sec
                                startTimer(21000);
                                updateCountDownText();
                                gif.setImageResource(gifList.get(5));
                                gif.setVisibility(View.VISIBLE);
                                btnStartPause.setVisibility(View.VISIBLE);
                                countDown.setVisibility(View.VISIBLE);
                                repeat.setVisibility(View.INVISIBLE);
                                btnNext.setVisibility(View.INVISIBLE); //cannot click next until times up (for timing workout)
                                break;

                            case 12: //workout 7
                                title.setText(sub1List.get(6));
                                stopTimer();
                                resetTimer(21000); //20 sec
                                startTimer(21000);
                                updateCountDownText();
                                gif.setImageResource(gifList.get(6));
                                gif.setVisibility(View.VISIBLE);
                                btnStartPause.setVisibility(View.VISIBLE);
                                countDown.setVisibility(View.VISIBLE);
                                repeat.setVisibility(View.INVISIBLE);
                                btnNext.setVisibility(View.INVISIBLE); //cannot click next until times up (for timing workout)
                                break;

                            case 14://workout 8
                                stopTimer();
                                title.setText(sub1List.get(7));
                                repeat.setText("15 times");
                                gif.setImageResource(gifList.get(7));
                                gif.setVisibility(View.VISIBLE);
                                countDown.setVisibility(View.INVISIBLE);
                                repeat.setVisibility(View.VISIBLE);
                                break;

                            case 16://workout 9
                                title.setText(sub1List.get(8));
                                stopTimer();
                                resetTimer(21000); //20 sec
                                startTimer(21000);
                                updateCountDownText();
                                gif.setImageResource(gifList.get(8));
                                gif.setVisibility(View.VISIBLE);
                                btnStartPause.setVisibility(View.VISIBLE);
                                countDown.setVisibility(View.VISIBLE);
                                repeat.setVisibility(View.INVISIBLE);
                                btnNext.setVisibility(View.INVISIBLE); //cannot click next until times up (for timing workout)

                            case 17://finish workout
                                pauseChronometer(); //added
                                stopTimer();
                                title.setVisibility(View.INVISIBLE);
                                countDown.setVisibility(View.INVISIBLE);
                                repeat.setVisibility(View.INVISIBLE);;
                                btnStartPause.setVisibility(View.INVISIBLE);
                                gif.setVisibility(View.INVISIBLE);
                                finish.setVisibility(View.VISIBLE); //added
                                countup.setTextColor(Color.parseColor("#FFFFFF"));//added
                                congratsText.setVisibility(View.VISIBLE);
                                congratsDesc.setVisibility(View.VISIBLE);
                                congratsDesc.setText("Workout finished:\nUpper Body Challenge\n(Advanced)\n\nCalories burn:\n95kcl"); //added
                                btnNext.setText("finish");
                                btnNext.setVisibility(View.VISIBLE);
                                break;

                            case 18:
                                finish();

                        }
                    }
                }
        });


        //for start/pause timer button
        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mTimerRunning) {
                    pauseTimer(); //if timer still runs pause timer
                } else {
                    resumeTimer(); //else resume timer
                    btnStartPause.setText("pause");

                }
            }
        });
    }

    //arraylist for workout title and gif
    public void challengeList(){

        sub1List.add("Jumping Jacks\n(Workout 1/9)");
        sub1List.add("Push-Ups\n(Workout 2/9)");
        sub1List.add("Bicycle Crunches\n(Workout 3/9)");
        sub1List.add("Wall Sit\n(Workout 4/9)");
        sub1List.add("Mountain Climber\n(Workout 5/9)");
        sub1List.add("Plank to Push-up\n(Workout 6/9)");
        sub1List.add("Side Plank Dip\n(Workout 7/9)");
        sub1List.add("Triceps Dips\n(Workout 8/9)");
        sub1List.add("Mountain Climber\n(Workout 9/9)");

        gifList.add(R.drawable.jumpingjacks);
        gifList.add(R.drawable.pushup);
        gifList.add(R.drawable.bicyclecrunches);
        gifList.add(R.drawable.wallsit);
        gifList.add(R.drawable.mountainclimber);
        gifList.add(R.drawable.planktopushup);
        gifList.add(R.drawable.sideplankdip);
        gifList.add(R.drawable.tricepdips);
        gifList.add(R.drawable.mountainclimber);

    }

    //timer resume settings
    private void resumeTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                btnStartPause.setText("pause"); //resume
                btnNext.setVisibility(View.VISIBLE);
                btnNext.callOnClick();
                vibrate.vibrate(300);
            }
        }.start();

        mTimerRunning = true;
        //btnStartPause.setText("resume"); //pause
    }


    //timer start settings
    private void startTimer(long START_TIME_IN_MILLIS) {

        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                btnStartPause.setText("pause"); //resume
                btnNext.setVisibility(View.VISIBLE);
                btnNext.callOnClick(); //call next workout
                vibrate.vibrate(300);
            }
        }.start();

        mTimerRunning = true;
    }

    //begin prestart timer
    private void beginTimer(long START_TIME_IN_MILLIS) {

        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateBeginTimer();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                btnStartPause.setText("pause");
                btnStartPause.setVisibility(View.INVISIBLE);
                btnNext.callOnClick(); //call next workout
                vibrate.vibrate(300);
            }
        }.start();

        mTimerRunning = true;
    }

    //timer pause settings
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        btnStartPause.setText("resume");
    }

    //timer reset settings
    private void resetTimer(long START_TIME_IN_MILLIS) {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    //timer stop settings
    private void stopTimer(){
        btnStartPause.setVisibility(View.INVISIBLE);
        mCountDownTimer.cancel();
    }

    //update textview
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        countDown.setText(timeLeftFormatted);
    }

    //update prestart timer
    private void updateBeginTimer() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        starting.setText(timeLeftFormatted);
    }

    //added
    public void startChronometer() {
        if (!running) {
            countup.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            countup.start();
            running = true;
        }
    }

    public void pauseChronometer() {
        if (running) {
            countup.stop();
            pauseOffset = SystemClock.elapsedRealtime() - countup.getBase();
            running = false;
        }
    }

    //added

    //when user press back button show give up dialogbox
    @Override
    public void onBackPressed() {
        showDialog();
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.giveup_dailogbox);

        TextView title = (TextView)dialog.findViewById(R.id.dialog_title);
        TextView desc = (TextView)dialog.findViewById(R.id.dialog_desc);
        title.setText("Really want to give up?");
        Random rand = new Random();
        int i =rand.nextInt(2);
        switch(i){
            case 0: desc.setText("Great things are never easy");
            break;
            case 1:desc.setText("Don't quit! Feeling tired means it's working");
                break;
            case 2:desc.setText("It's now or never!");
                break;
        }
        Button btnQuit =(Button)dialog.findViewById(R.id.buttonQuit);

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                countup.stop(); //added
                Intent i = new Intent(Startchallenge7.this, MainActivity.class);
                startActivity(i);
            }
        });

        Button btnContinue =(Button)dialog.findViewById(R.id.buttonContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        //dialogbox resizing
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        int dialogWindowHeight = (int) (displayHeight * 0.4f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        dialog.getWindow().setAttributes(layoutParams);
    }
}