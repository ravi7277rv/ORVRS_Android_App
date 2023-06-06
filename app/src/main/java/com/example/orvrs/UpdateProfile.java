package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfile extends AppCompatActivity {


    private EditText ediTxtName,ediTxtMobile,ediTxtEmail;
    private String name,mobile,email;
    private FirebaseAuth fAuth;
    private Button btnUploadProfilePic,btnUpdateNewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        ediTxtName = findViewById(R.id.ediTxtName);
        ediTxtEmail = findViewById(R.id.ediTxtEmail);
        ediTxtMobile = findViewById(R.id.ediTxtMobile);

//        btnUploadProfilePic = findViewById(R.id.btnUploadProfilePic);
        btnUpdateNewProfile = findViewById(R.id.btnUpdateNewProfile);



        
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        //Show Profile Data
        showProfile(firebaseUser);



        btnUpdateNewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        //Upload Profile Pic
//        btnUploadProfilePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(UpdateProfile.this,UploadProfilePic.class);
//                startActivity(intent);
//                finish();
//            }
//        });


    }

    private void updateNewProfileData(FirebaseUser firebaseUser) {
        String mobileRegex = "[7-9][0-9]{9}";//First no. may be(7 or 8 or 9)and rest 9 no. can be any no.
        Matcher mobileMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(mobile);

        if (name.trim().isEmpty()) {
            ediTxtName.setError("Email is required");
            ediTxtName.requestFocus();
            return;
        } else if (!mobileMatcher.find()) {
            ediTxtMobile.setError("Mobile no. is not valid");
            ediTxtMobile.requestFocus();
            return;
        } else if (email.trim().isEmpty()) {
            ediTxtEmail.setError("Email is required");
            ediTxtEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ediTxtEmail.setError("Valid Email required");
            ediTxtEmail.requestFocus();
            return;
        } else{
            //Obtained the data entered by the user
            name = ediTxtName.getText().toString();
            email = ediTxtEmail.getText().toString();
            mobile = ediTxtMobile.getText().toString();

            //Enter the user data to the Realtime database
            UserModel writeUserDetails = new UserModel(name,mobile,email);

             //Extract User reference from Database for "RegisteredUser"
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("RegisteredUser");

            String userId = firebaseUser.getUid();

            referenceProfile.child(userId).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        //Setting new display Name
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build();
                        firebaseUser.updateProfile(profileUpdate);

                        Toast.makeText(UpdateProfile.this, "Update Successful", Toast.LENGTH_SHORT).show();


                        //stop uer from returning to updateProfile on pressing back
                        Intent intent = new Intent(UpdateProfile.this,UserProfile.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else {
                        try{
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(UpdateProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private void showProfile(FirebaseUser firebaseUser) {
        String userIdOfRegistered = firebaseUser.getUid();

        //Extracting User Reference from Database for "Registered User"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("RegisteredUser");

        referenceProfile.child(userIdOfRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel readWriteUserDetails = snapshot.getValue(UserModel.class);
                if(readWriteUserDetails != null){
                    name = readWriteUserDetails.name;
                    email = firebaseUser.getEmail();
                    mobile = readWriteUserDetails.mobile;

                    ediTxtName.setText(name);
                    ediTxtEmail.setText(email);
                    ediTxtMobile.setText(mobile);

                }else {
                    Toast.makeText(UpdateProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}