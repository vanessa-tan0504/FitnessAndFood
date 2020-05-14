package com.example.FitnessAndFood;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;

public class DrawerFragment_profile extends Fragment {

    RecyclerView recyclerView;
    View v;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private StorageReference storageRef,imgRef;
    ArrayList<String> profileList = new ArrayList<>();
    ArrayList<String> descList = new ArrayList<>();
    ArrayList<String>  tempList = new ArrayList<String>();
    String name,weight,height,bmi,gender,email;
    ImageView imageView; //Added
    Uri selectedImg;
    ProgressBar progressBar4,progressBar5;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.drawer_fragment_profile,container,false);

        recyclerView = v.findViewById(R.id.rv_profile);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference();

        imageView = v.findViewById(R.id.pictest); //added
        progressBar4 = v.findViewById(R.id.progressBar4);
        progressBar4.setVisibility(View.GONE);
        progressBar5 = v.findViewById(R.id.progressBar5);
        imageView.setOnClickListener(new View.OnClickListener() { //added
            @Override
            public void onClick(View v) {
                selectImage(getContext());
            }
        });

        //Get profile details from Firestore of logged in user
        db.collection("Users").whereEqualTo("email",user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document:task.getResult()){
                        name = document.getString("name");
                        weight = document.getString("weight");
                        height = document.getString("height");
                        bmi = document.getString("bmi");
                        gender = document.getString("gender");
                        email = document.getString("email");
                        profileList(name,weight,height,bmi,gender,email);
                        if(document.getString("profpic") != null){
                            Glide.with(v.getContext()).load(document.getString("profpic")).into(imageView);
                        }
                    }
                }
            }
        });

        return v;
    }

    //added
    private void selectImage(final Context context) {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

               if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

               } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
               }
            }
        });
        builder.show();
    }

    //upload image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_CANCELED) {
            //upload from gallery
            if (data != null && requestCode == 1) {
                selectedImg = data.getData();
                imgRef = storageRef.child("ProfilePicture/" + name + "ProfileImg");
                if (selectedImg != null && !selectedImg.equals(Uri.EMPTY)) {
                    imgRef.putFile(selectedImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> imgURL = taskSnapshot.getStorage().getDownloadUrl();
                            while (!imgURL.isSuccessful()) ;
                            Uri imgUri = imgURL.getResult();
                            db.collection("Users").document(user.getEmail()).update("profpic", imgUri.toString());
                            Glide.with(v.getContext()).load(imgUri.toString()).into(imageView);
                            Toast.makeText(v.getContext(), "Profile Picture Successfully Updated!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(v.getContext(), MainActivity.class));
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar4.setVisibility(View.VISIBLE);
                            double progress = (100.00 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressBar4.setProgress((int) progress);
                        }
                    });
                }
            }
        }

    }

    public void profileList(String n, String w, String h, String b, String g, String e){

        //title arraylist
        profileList.add("Name");
        profileList.add("Weight");
        profileList.add("Height");
        profileList.add("BMI");
        profileList.add("Gender");
        profileList.add("Email");

        //description arraylist
        descList.add(n);
        descList.add(w);
        descList.add(h);
        descList.add(b);
        descList.add(g);
        descList.add(e);

        tempList.add(n);
        tempList.add(w);
        tempList.add(h);
        tempList.add(b);
        tempList.add(g);
        tempList.add(e);

        progressBar5.setVisibility(View.GONE);
        ProfileRVAdapter profileRVAdapter =
                new ProfileRVAdapter(getActivity(),profileList,descList,tempList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(profileRVAdapter);
    }



}
