package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AdminLogin extends AppCompatActivity {

    private Button btnAdminSignIn;
    private EditText ediTextAdminEmail,ediTextAdminPass;
    private  String adminEmail;
    private  String adminPassword;
    private ProgressDialog loading;
    private String email,pass;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getSupportActionBar().setTitle("Admin SignIn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ediTextAdminEmail = findViewById(R.id.editTxtAdminEmail);
        ediTextAdminPass = findViewById(R.id.editTxtAdminPassword);
        btnAdminSignIn = findViewById(R.id.btnAdminSignIn);

// ============== Loading Progress bar
        loading = new ProgressDialog(this);



// ============Getting the reference of the database ==================
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Admin");


       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               for(DataSnapshot ds : snapshot.getChildren()) {
                   adminEmail = ds.child("email").getValue(String.class);
                   adminPassword = ds.child("pass").getValue(String.class);
//                   Toast.makeText(AdminLogin.this, adminEmail+" "+adminPassword, Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

// ============Getting the reference of the database ==================



// ================ AdminSignIn button clicked =========================
        btnAdminSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loading.setMessage("loading...");
                loading.setCancelable(false);


                 email = ediTextAdminEmail.getText().toString();
                 pass = ediTextAdminPass.getText().toString();

                if (email.trim().isEmpty()) {
                    ediTextAdminEmail.setError("Email is required");
                    ediTextAdminEmail.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ediTextAdminEmail.setError("Valid Email required");
                    ediTextAdminEmail.requestFocus();
                    return;
                } else if (pass.isEmpty()) {
                    ediTextAdminPass.setError("Password required");
                    ediTextAdminPass.requestFocus();
                    return;
                }else {
                    loading.show();
                    adminLogin(email,pass);
                }

            }
        });
// ================ AdminSignIn button clicked =========================


    }
// ==================== Admin Login Method ==============================
    private void adminLogin(String email, String pass) {
//        Toast.makeText(this, email+" "+adminEmail, Toast.LENGTH_SHORT).show();

            if(Objects.equals(adminEmail, email) && Objects.equals(adminPassword,pass)){
                loading.dismiss();
                startActivity(new Intent(AdminLogin.this,AdminDashboard.class));
                finish();
            }else {
                loading.dismiss();
                Toast.makeText(this, "Invalid Credential", Toast.LENGTH_SHORT).show();
            }



        }

// ==================== Admin Login Method ==============================
    }
