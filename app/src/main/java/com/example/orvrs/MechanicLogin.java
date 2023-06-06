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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MechanicLogin extends AppCompatActivity {

    private EditText editMecLogPhone,editMecLogPassword;
    private Button btnMecLogSignIn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String m_Phone,m_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_login);

            getSupportActionBar().setTitle("Mechanic SignIn");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            editMecLogPhone = findViewById(R.id.editMecLogEmail);
            editMecLogPassword = findViewById(R.id.editMecLogPassword);
            btnMecLogSignIn = findViewById(R.id.btnMecLogSignIn);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Mechanics");

            btnMecLogSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     m_Phone = editMecLogPhone.getText().toString();
                     m_pass = editMecLogPassword.getText().toString();

                    if(m_Phone.trim().isEmpty()){
                        editMecLogPhone.setError("Email is required");
                        editMecLogPhone.requestFocus();
                        return;
                    }else if (!Patterns.PHONE.matcher(m_Phone).matches()){
                        editMecLogPhone.setError("Valid Phone is required");
                        editMecLogPhone.requestFocus();
                        return;
                    }else if (m_pass.trim().isEmpty()){
                        editMecLogPassword.setError("Password is required");
                        editMecLogPassword.requestFocus();
                        return;
                    }else {

                        allowToAccount(m_Phone,m_pass);

                    }

                }
            });
    }

    private void allowToAccount(String phon, String pss) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(phon).exists()){
                    MechanicsModel mModel = snapshot.child(phon).getValue(MechanicsModel.class);

                    if (mModel.getPhone().equals(phon)){
                        if (mModel.getPassword().equals(pss)){
                            Toast.makeText(MechanicLogin.this, "Welcome", Toast.LENGTH_SHORT).show();
                            Prevalent.currentOnlineMechanic = mModel;
                            startActivity(new Intent(MechanicLogin.this,MechanicDashboard.class));
                        }else{
                            Toast.makeText(MechanicLogin.this, "wrong password", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(MechanicLogin.this, "Mechanic not exist", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MechanicLogin.this, "Account Does not Exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


// ========================== Signing In Mechanic after getting the data from the database ==========================
//    private void signInMechanic(String m_email, String m_pass) {
//
//        if(mecEmail == m_email && mecPassword == m_pass){
//            Toast.makeText(this, "Mechanic SignIn Successfully", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MechanicLogin.this,MechanicDashboard.class));
//            finish();
//        }else {
//            Toast.makeText(this, "Invalid Credential", Toast.LENGTH_SHORT).show();
//        }
//    }
// ========================== Signing In Mechanic after getting the data from the database ==========================





// ============================ Checking Whether Mechanic Exist or not in the databases =============================
//    private void checkMechanic(String m_email) {
//
//        databaseReference.child(m_email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//                if(task.isSuccessful()){
//
//                    if(task.getResult().exists()){
//                        //getting the data in snapshot from the task
//                        DataSnapshot mec_data = task.getResult();
//                        // getting the data to the variable from the mec_data
//                        mecEmail = String.valueOf(mec_data.child("email").getValue());
//                        mecPassword = String.valueOf(mec_data.child("password").getValue());
//
//
//                    }else {
//
//                        Toast.makeText(MechanicLogin.this, "Mechanic does not exist.", Toast.LENGTH_SHORT).show();
//                    }
//
//                }else {
//
//                    Toast.makeText(MechanicLogin.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//// ============================ Checking Whether Mechanic Exist or not in the databases =============================
//
//    }
}