package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupScreen extends AppCompatActivity {

    private EditText ediTxtUserName, ediTxtMobile, ediTxtEmail, ediTxtPassword, ediTxtCPassword;
    private TextView txtSignIn;
    private Button btnSignUp;
    private FirebaseAuth fAuth;
    private static final String TAG = "SignupScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        getSupportActionBar().setTitle("SignUp here..");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ediTxtUserName = findViewById(R.id.editTxtUser);
        ediTxtMobile = findViewById(R.id.editTxtMobile);
        ediTxtEmail = findViewById(R.id.editTxtSEmail);
        ediTxtPassword = findViewById(R.id.editTxtSPassword);
        ediTxtCPassword = findViewById(R.id.editTxtCPassword);



        txtSignIn = findViewById(R.id.txtSSignIn);
        btnSignUp = findViewById(R.id.btnSSignUp);


        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iSignIn = new Intent(SignupScreen.this, LoginScreen.class);
                startActivity(iSignIn);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = ediTxtUserName.getText().toString();
                String mobile = ediTxtMobile.getText().toString();
                String email = ediTxtEmail.getText().toString();
                String password = ediTxtPassword.getText().toString();
                String confirmPass = ediTxtCPassword.getText().toString();

                String mobileRegex = "[7-9][0-9]{9}";//First no. may be(7 or 8 or 9)and rest 9 no. can be any no.
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(mobile);

                if (name.trim().isEmpty()) {
                    ediTxtUserName.setError("Email is required");
                    ediTxtUserName.requestFocus();
                    return;
                } else if (!mobileMatcher.find()) {
                    Toast.makeText(SignupScreen.this, "Please re-enter mobile no.", Toast.LENGTH_SHORT).show();
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
                } else if (password.isEmpty()) {
                    ediTxtPassword.setError("Password required");
                    ediTxtPassword.requestFocus();
                    return;
                } else if (password.length() < 5) {
                    ediTxtPassword.setError("At least six digit pass required");
                    ediTxtPassword.requestFocus();
                } else if (confirmPass.isEmpty()) {
                    ediTxtCPassword.setError("Confirm Pass required");
                    ediTxtCPassword.requestFocus();
                    return;
                } else if (!confirmPass.equals(password)) {
                    ediTxtCPassword.setError("Password doesn't match");
                    ediTxtCPassword.requestFocus();

                    // clear the entered password of the both fields.
                    ediTxtPassword.clearComposingText();
                    ediTxtCPassword.clearComposingText();
                }
                    registerUser(name, mobile, email, password);



            }

        });
    }

    private void registerUser(String name, String mobile, String email, String password) {

        fAuth = FirebaseAuth.getInstance();
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = fAuth.getCurrentUser();

                    UserModel writeUserDetails = new UserModel(name,mobile,email,password);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("RegisteredUser");

                    databaseReference.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupScreen.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignupScreen.this,UserDashboard.class);
                                //To prevent User from returning back to Register Activity on pressing back button after registration
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); // to close Register Activity

                            }else{

                                Toast.makeText(SignupScreen.this, "User Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                            //progressBar.setVisibility(View.GONE);
                        }
                    });
                }else{
                    try {
                        throw  task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        ediTxtPassword.setError("Your password is too weak. Kindly use a mix of alphabets,number and special character");
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        ediTxtEmail.setError("Your email is invalid or already in use. Kindly re-enter");
                    }catch(FirebaseAuthUserCollisionException e){
                        ediTxtEmail.setError("User is already registered with this email. Use another email.");
                    }catch(Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(SignupScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    //Hide progressbar whether user creation is successful or failed
                    //progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

}