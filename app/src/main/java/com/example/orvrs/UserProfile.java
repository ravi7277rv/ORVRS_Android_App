package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    //private ImageView imgProfilePic;
    private TextView txtWelcome,txtUserName,txtUserEmail,txtUserPhone;
    private FirebaseAuth auth;
    private String fullname,email,mobile;
    private Button btnUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().setTitle("UserProfile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //imgProfilePic = findViewById(R.id.imgUserProfile);
        txtWelcome = findViewById(R.id.txtShow_Welcome);
        txtUserName = findViewById(R.id.txtUser_name);
        txtUserEmail = findViewById(R.id.txtUser_email);
        txtUserPhone = findViewById(R.id.txtUser_phone);
        btnUpdateProfile = findViewById(R.id.btnUpdate_profile);


        // Update Profile
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(UserProfile.this,UpdateProfile.class);
                    startActivity(intent);
            }
        });





        //for setting the imageProfile pic
//        imgProfilePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(UserProfile.this,UploadProfilePic.class));
//            }
//        });

        //Getting the instance of current user and checking it
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null){
            Toast.makeText(UserProfile.this, "Something Went wrong !  Details are not available at the moment", Toast.LENGTH_SHORT).show();
        }else {
            showUserProfile(firebaseUser);
        }
    }


    //fetching the data from the database and showing to the user while setText();
    private void showUserProfile(FirebaseUser firebaseUser) {

        String userId = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("RegisteredUser");
        referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserModel readWriteUserDetails = snapshot.getValue(UserModel.class);

                if(readWriteUserDetails != null){
                    //getting the data from the database
                    fullname = readWriteUserDetails.name; //firebaseUser.getDisplayName();
                    email = readWriteUserDetails.email; //firebaseUser.getEmail();
                    mobile = readWriteUserDetails.mobile;

                    //setting the data to the text field
                    txtWelcome.setText("Welcome "+fullname+"!");
                    txtUserName.setText(fullname);
                    txtUserEmail.setText(email);
                    txtUserPhone.setText(mobile);


                    //Set User Display Picture
                   // Uri uri = firebaseUser.getPhotoUrl();

                    //Image Viewer setImageURI() should not ve used with regular URIs so we are using Picasso
                    //Picasso.get().load(uri).into(imgProfilePic);
                }else {
                    Toast.makeText(UserProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}