package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginScreen extends AppCompatActivity {

    EditText ediTextEmail,ediTextPassword;
    TextView txtForgetPass,txtSignup;
    Button btnSignIn,btnAdmin,btnMechanic;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "LoginScreen";
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        getSupportActionBar().setTitle("SignIn here..");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ediTextEmail = findViewById(R.id.editTxtEmail);
        ediTextPassword = findViewById(R.id.editTxtPassword);
        txtForgetPass = findViewById(R.id.txtForgetPass);
        txtSignup = findViewById(R.id.txtSignup);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnAdmin = findViewById(R.id.btnAdmSignIn);
        btnMechanic = findViewById(R.id.btnMecSignIn);

        loading = new ProgressDialog(this);


        firebaseAuth = FirebaseAuth.getInstance();


        //RESET PASSWORD
        txtForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreen.this, "Now you can reset your password", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginScreen.this,ForgetPassword.class));
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading.setMessage("loading...");
                loading.setCancelable(false);


                String email = ediTextEmail.getText().toString();
                String password = ediTextPassword.getText().toString();

                if (email.trim().isEmpty()) {
                    ediTextEmail.setError("Email is required");
                    ediTextEmail.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ediTextEmail.setError("Valid Email required");
                    ediTextEmail.requestFocus();
                    return;
                } else if (password.isEmpty()) {
                    ediTextPassword.setError("Password required");
                    ediTextPassword.requestFocus();
                    return;
                }

                else {
                    //Toast.makeText(LoginScreen.this, "Inside the firebaseAuth", Toast.LENGTH_SHORT).show();
                    loading.show();
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Toast.makeText(LoginScreen.this, "After the firebase", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginScreen.this, "User LoggedIn Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginScreen.this, UserDashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                try {
                                    throw task.getException();
                                }catch (FirebaseAuthInvalidUserException e){
                                    //ediTextEmail.setError("User does not exists or no longer valid. Please register again.");
                                    Toast.makeText(LoginScreen.this, "User does not exist.", Toast.LENGTH_SHORT).show();
                                }catch(FirebaseAuthInvalidCredentialsException e){
                                   //ediTextEmail.setError("Invalid credential. Kindly, check and re-enter.");
                                    Toast.makeText(LoginScreen.this, "Invalid Credential.", Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Log.e(TAG, e.getMessage());
                                    Toast.makeText(LoginScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                               // Toast.makeText(LoginScreen.this, "User LogIn Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        //Signup button will take you to the SignUp Screen
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logIntent = new Intent(LoginScreen.this,SignupScreen.class);
                startActivity(logIntent);
            }
        });


        //ForgetPassword Button will take you to the screen for setting the password
        txtForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgetPass = new Intent(LoginScreen.this,ForgetPassword.class);
                startActivity(forgetPass);
            }
        });



        //AdminSignIn button will take you to the Admin SignIn Screen
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this,AdminLogin.class));
            }
        });



        //MechanicSignIn button will take you to the Mechanic SignIn Screen
        btnMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this,MechanicLogin.class));
            }
        });
    }




    //check if user is already logged in. In such case, straightway take the user to the User's Dashboard
    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(this, "Already Logged In!", Toast.LENGTH_SHORT).show();

            //Start the UserProfile Activity
            startActivity(new Intent(LoginScreen.this,UserDashboard.class));
            finish();
        }else {
            Toast.makeText(this, "You can login Now", Toast.LENGTH_SHORT).show();
        }
    }
}