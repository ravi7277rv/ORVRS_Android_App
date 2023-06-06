package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class ForgetPassword extends AppCompatActivity {

    private EditText ediTxtResetPass;
    private Button btnResetPassword;
    private FirebaseAuth fAuth;
    private static final String TAG = "ForgetPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        getSupportActionBar().setTitle("Forget Password");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ediTxtResetPass = findViewById(R.id.ediTxtResetPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ediTxtResetPass.getText().toString();

                if(TextUtils.isEmpty(email)){
                    ediTxtResetPass.setError("Email is required");
                    ediTxtResetPass.requestFocus();
                }else  if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    ediTxtResetPass.setError("Valid Email is required");
                    ediTxtResetPass.requestFocus();
                }else{
                    resetPassword(email);
                }
            }
        });

    }

    private void resetPassword(String email) {
        fAuth = FirebaseAuth.getInstance();
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Please check your inbox for password reset link",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ForgetPassword.this,LoginScreen.class);
                    startActivity(intent);


                    //clear stack to prevent user coming back to forgot password activity
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                   startActivity(intent);
//                    finish();

                }else {
                    try{
                         throw task.getException();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        ediTxtResetPass.setError("User does not exists or is no longer valid. Please register again.");
                        ediTxtResetPass.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(ForgetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}