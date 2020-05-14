package com.example.FitnessAndFood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText email;
    private EditText password;
    private Button btnlogin;
    private TextView signup;
    private SessionManager session;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        btnlogin = findViewById(R.id.btn_login);
        signup= findViewById(R.id.link_signup);

        session = new SessionManager(this);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        //if validation fail, go to toast message from onLoginFailed and show error field
        if (!validate()) {
            onLoginFailed();
            return;
        }

        //else if validation okay

        btnlogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String useremail = email.getText().toString();
        String userpassword = password.getText().toString();

        // TODO: Implement your own authentication logic here.
        auth.signInWithEmailAndPassword(useremail,userpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                db.collection("Users").whereEqualTo("email",useremail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful())
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String username = document.getString("name");
                                                onLoginSuccess(username,useremail);
                                            }
                                    }
                                });
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onLoginFailed();
                progressDialog.dismiss();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String userName,String userEmail) {
        btnlogin.setEnabled(true);
        //SESSION
        session.createLoginSession(userName,userEmail); // need to put user login email and password
        Intent i = new Intent(this,MainActivity.class); //if log in success, go to main activity
        startActivity(i);
        //SESSION
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnlogin.setEnabled(true);
    }

    //validation for user input fields
    public boolean validate() {
        boolean valid = true;

        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();

        if (useremail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (userpassword.isEmpty() || userpassword.length() < 4 || userpassword.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }
}
