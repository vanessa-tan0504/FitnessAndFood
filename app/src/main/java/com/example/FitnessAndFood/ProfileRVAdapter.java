package com.example.FitnessAndFood;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class ProfileRVAdapter extends RecyclerView.Adapter<ProfileRVAdapter.ViewHolder> {

    ArrayList<String> profileList = new ArrayList<String>();
    ArrayList<Integer> counter = new ArrayList<Integer>();
    ArrayList<String>  descList = new ArrayList<String>();
    ArrayList<String>  tempList = new ArrayList<String>();
    Context context;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private String field ="";
    private String height="",weight="";
    private double BMI;

    public ProfileRVAdapter(Context context,
                            ArrayList<String> profileList,
                            ArrayList<String> descList,
                            ArrayList<String> tempList) {
        this.profileList = profileList;
        this.descList = descList;
        this.context = context;
        this.tempList = tempList;

        for (int i = 0; i < profileList.size(); i++) {
            counter.add(0);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc;
        ConstraintLayout view;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.profile_title);
            desc = itemView.findViewById(R.id.profile_desc);
            view = itemView.findViewById(R.id.profile_LayoutHolder);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_list, parent, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //To get the weight and height first before changes are made
        db.collection("Users").document(user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                height = documentSnapshot.getString("height");
                weight = documentSnapshot.getString("weight");
            }
        });

        ProfileRVAdapter.ViewHolder vh = new ProfileRVAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Setting user's details display
        holder.title.setText(profileList.get(position));
        holder.desc.setText(descList.get(position));
        //onclick to another  activity for challenge
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(position){
                    case 0: //name
                        showCustomDialog(position,holder);
                        break;
                    case 1: //weight
                        showCustomDialog2(position,holder);
                        break;
                    case 2://height
                        showCustomDialog2(position,holder);
                        break;
                    case 3: //bmi
                        //BMI will be automatically calculated
                        tempList.add(position,String.format("%.2f",BMI));
                        holder.desc.setText(tempList.get(position));
                        break;
                    case 4://gender
                        showCustomDialog3(position,holder);
                        break;
                    case 5://email
                        //EMAIL is a crucial ID and mustn't be changed
                        break;
                }

            }
        });
   }

    @Override
    public int getItemCount() {

        return profileList.size();
    }

    // for name dialog box
    private void showCustomDialog(final int position, final ViewHolder holder){
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.profile_dialogbox);

        TextView title = (TextView)dialog.findViewById(R.id.dialog_profileTitle);
        final EditText editText = (EditText)dialog.findViewById(R.id.dialog_profileET);
        title.setText(profileList.get(position));

        Button btnokay=(Button)dialog.findViewById(R.id.buttonOk);
        Button btncancel =(Button)dialog.findViewById(R.id.buttonCancel);

        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        field = "name";

        btnokay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userInput=editText.getText().toString();
                tempList.add(position,userInput);
                holder.desc.setText(tempList.get(position));
                //CHG
                db.collection("Users").document(user.getEmail()).update(field,userInput).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,  "Username successfully updated! ", Toast.LENGTH_SHORT).show();
                    }
                });
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
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        int dialogWindowHeight = (int) (displayHeight * 0.6f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        dialog.getWindow().setAttributes(layoutParams);
    }

    // for weight and height
    private void showCustomDialog2(final int position, final ViewHolder holder){
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.profile_dialogbox2);

        TextView title = (TextView)dialog.findViewById(R.id.dialog_profileTitle);
        final EditText editText = (EditText)dialog.findViewById(R.id.dialog_profileET);
        title.setText(profileList.get(position));

        Button btnokay=(Button)dialog.findViewById(R.id.buttonOk);
        Button btncancel =(Button)dialog.findViewById(R.id.buttonCancel);
        final NumberPicker np = (NumberPicker)dialog.findViewById(R.id.dialog_preparePicker);

        switch(position){
            case 1:
                np.setMinValue(40);
                np.setMaxValue(200);
                field = "weight";
                break;
            case 2:
                np.setMinValue(140);
                np.setMaxValue(210);
                field = "height";
                break;
        }

        btnokay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int picked = np.getValue();
                final String userInput = Integer.toString(picked);
                tempList.add(position,userInput);
                holder.desc.setText(tempList.get(position));
                //CHG
                db.collection("Users").document(user.getEmail()).update(field,userInput).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, field + " successfully updated! ", Toast.LENGTH_SHORT).show();
                        if(field.equals("height")){
                            if(weight.length()>0) {
                                BMI = Double.parseDouble(weight) / Math.pow(Double.parseDouble(userInput) / 100.00, 2);
                                db.collection("Users").document(user.getEmail()).update("bmi", String.format("%.2f", BMI)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "BMI has been updated!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        else if(field.equals("weight")){
                            db.collection("Users").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.getResult().getString("lightest").length()<=0 || task.getResult().getString("heaviest").length()<=0){
                                        weight = userInput;
                                        db.collection("Users").document(user.getEmail()).update("lightest",userInput,"heaviest",userInput);
                                    }
                                }
                            });
                            db.collection("Users").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.getResult().getString("lightest").length()>0 &&task.getResult().getString("heaviest").length()>0) {
                                        if (Integer.parseInt(userInput) < Integer.parseInt(task.getResult().getString("lightest"))) {
                                            db.collection("Users").document(user.getEmail()).update("lightest", userInput);
                                        }
                                        if (Integer.parseInt(userInput) > Integer.parseInt(task.getResult().getString("heaviest"))) {
                                            db.collection("Users").document(user.getEmail()).update("heaviest", userInput);
                                        }
                                    }
                                }
                            });
                            if(height.length()>0) {
                                BMI = Double.parseDouble(userInput) / Math.pow(Double.parseDouble(height) / 100.00, 2);
                                db.collection("Users").document(user.getEmail()).update("bmi", String.format("%.2f", BMI)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "BMI has been updated!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            db.collection("Users").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(Integer.parseInt(task.getResult().getString("counter"))==0){
                                        db.collection("Users").document(user.getEmail()).update("counter","1","history1",userInput);
                                    }
                                    else if(Integer.parseInt(task.getResult().getString("counter"))==1){
                                        db.collection("Users").document(user.getEmail()).update("counter","2","history2",userInput);
                                    }
                                    else if(Integer.parseInt(task.getResult().getString("counter"))==2){
                                        db.collection("Users").document(user.getEmail()).update("counter","3","history3",userInput);
                                    }
                                    else if(Integer.parseInt(task.getResult().getString("counter"))==3){
                                        db.collection("Users").document(user.getEmail()).update("counter","4","history4",userInput);
                                    }
                                    else if(Integer.parseInt(task.getResult().getString("counter"))==4){
                                        db.collection("Users").document(user.getEmail()).update("counter","5","history5",userInput);
                                    }
                                    else{
                                        int updateCounter = Integer.parseInt(task.getResult().getString("counter"))+1;
                                        db.collection("Users").document(user.getEmail()).update("counter",Integer.toString(updateCounter));
                                        db.collection("Users").document(user.getEmail()).update("history1",task.getResult().getString("history2"));
                                        db.collection("Users").document(user.getEmail()).update("history2",task.getResult().getString("history3"));
                                        db.collection("Users").document(user.getEmail()).update("history3",task.getResult().getString("history4"));
                                        db.collection("Users").document(user.getEmail()).update("history4",task.getResult().getString("history5"));
                                        db.collection("Users").document(user.getEmail()).update("history5",userInput);
                                    }
                                }
                            });
                        }
                    }
                });

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

    //for gender dialogbox
    private void showCustomDialog3(final int position, final ViewHolder holder){
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.profile_dialogbox2);

        TextView title = (TextView)dialog.findViewById(R.id.dialog_profileTitle);
        final NumberPicker np = (NumberPicker)dialog.findViewById(R.id.dialog_preparePicker);
        title.setText(profileList.get(position));

        np.setMinValue(0);
        np.setMaxValue(1);
        np.setDisplayedValues(new String[]{"Male","Female"});

        Button btnokay=(Button)dialog.findViewById(R.id.buttonOk);
        Button btncancel =(Button)dialog.findViewById(R.id.buttonCancel);




        btnokay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = np.getValue();
                String userInput;
                if(selected<1){
                    userInput = "Male";
                }
                else{
                    userInput = "Female";
                }
                tempList.add(position,userInput);
                holder.desc.setText(tempList.get(position));
                //Toast.makeText(context, userInput, Toast.LENGTH_SHORT).show();
                db.collection("Users").document(user.getEmail()).update("gender",userInput).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Gender successfully updated!", Toast.LENGTH_SHORT).show();
                    }
                });
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
