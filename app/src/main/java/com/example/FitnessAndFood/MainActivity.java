package com.example.FitnessAndFood;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView username,useremail;
    private  DrawerLayout drawer;
    private NavigationView navigationView;
    private BottomNavigationView nav;
    private DrawerFragment_profile fragment;
    private View headerView;
    SessionManager session; //SESSION
    private FirebaseAuth auth;
    private FirebaseUser curUser;
    private FirebaseFirestore db;
    private ImageView profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        //go to log in first everytime when launch
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);

        // mTextView = (TextView) mTextView.findViewById(R.id.tv_profile);
       // mTextMessage = (TextView) findViewById(R.id.message);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view); //side nav bar
        headerView = navigationView.getHeaderView(0);
        username = (TextView) headerView.findViewById(R.id.username);
        useremail = (TextView) headerView.findViewById(R.id.useremail);
        navigationView.setNavigationItemSelectedListener(this);
        profilepic = (ImageView) headerView.findViewById(R.id.profilepic);

        //SESSION STARTS (status false)
        session = new SessionManager(this);
        auth = FirebaseAuth.getInstance();
        curUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        // assign logged in name on Nav Drawer
        db.collection("Users").document(curUser.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String name = task.getResult().getString("name");
                username.setText(name);
                String email = task.getResult().getString("email");
                useremail.setText(email);
                Glide.with(getApplicationContext()).load(task.getResult().getString("profpic")).into(profilepic);
                Toast.makeText(MainActivity.this, "Welcome "+ name + "!", Toast.LENGTH_LONG).show();
            }
        });



        FragmentManager manager = getSupportFragmentManager();
        //fragment = (DrawerFragment_profile) manager.findFragmentById(R.id.profile_fragment);

        //side nav bar action
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //first fragment item(workout) appear when launch
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new BottomNavFragment_workout()).commit();


        //bottom nav bar
        nav = (BottomNavigationView) findViewById(R.id.navigation);
        nav.setSelectedItemId(R.id.workout);
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.news:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new BottomNavFragment_news()).commit();
                        return true;
                    case R.id.workout:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new BottomNavFragment_workout()).commit();
                        return true;
                    case R.id.diet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new BottomNavFragment_diet()).commit();
                        return true;
                    case R.id.challenge:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new BottomNavFragment_challenge()).commit();
                        return true;
                }
                return false;
            }
        };
        nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //for device's back button
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) { //if side drawer is open, user press back will close drawer
            drawer.closeDrawer(GravityCompat.START);
        }
        else { //if side drawer is close, user press back will ask if user want to exit app or not
            //super.onBackPressed();
            showDialog();

        }
    }

    //side nav bar
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch(item.getItemId()){
            //from activity_main_drawer.xml
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DrawerFragment_profile()).commit();
                break;
            case R.id.report:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DrawerFragment_report()).commit();
                break;
            case R.id.restart:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new DrawerFragment_restart()).commit();
                db.collection("Users").document(curUser.getEmail()).update("height","","weight","","lightest","","heaviest","","history1","","history2","","history3","","history4","","history5","","counter","0","bmi","");
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                Toast.makeText(this, "Progress Restarted!", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.logout:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new DrawerFragment_logout()).commit();
                session.logoutUser();
                auth.signOut();
                break;
        }
        //drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //backbutton dialogbox
    public void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.logout_dialogbox);

        Button btnokay=(Button)dialog.findViewById(R.id.buttonOk);
        Button btncancel=(Button)dialog.findViewById(R.id.buttonCancel);

        btnokay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.cancel();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    }


}
