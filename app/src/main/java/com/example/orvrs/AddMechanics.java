package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMechanics extends AppCompatActivity {

    private RecyclerView recyclerMechanics;
    MechanicsAdapter mechanicsAdapter;
    private FloatingActionButton floatingActionButtonAddMechanics;
    private String mec_Id;
    private FirebaseAuth  firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mechanics);

        getSupportActionBar().setTitle("Mechanics Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerMechanics = findViewById(R.id.recyclerMechanic);
        floatingActionButtonAddMechanics = findViewById(R.id.floatingBtnAddMechanic);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Mechanics");



// ====================== Mechanics Viewing In AddMechanics ===================================
        recyclerMechanics.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MechanicsModel> options =
                new FirebaseRecyclerOptions.Builder<MechanicsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Mechanics"), MechanicsModel.class)
                        .build();


        mechanicsAdapter = new MechanicsAdapter(options);
        recyclerMechanics.setAdapter(mechanicsAdapter);
// ====================== Mechanics Viewing In AddMechanics ===================================

// ====================== Floating Button Add Mechanics Start here =======================================

        floatingActionButtonAddMechanics.setOnClickListener(new View.OnClickListener() {

            EditText ediTextMecName,ediTextMecEmail,ediTextMecPhone,ediTextMecCharges,
                    ediTextMecExp_year,ediTextMecExpert_In,ediTextMecAddress,ediTextMecPassword;

            Button btnAddMechanic;

            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(AddMechanics.this);
                dialog.setContentView(R.layout.add_mechanic_dialog);

                ediTextMecName = dialog.findViewById(R.id.ediTxtMecName);
                ediTextMecEmail = dialog.findViewById(R.id.ediTxtMecEmail);
                ediTextMecPhone = dialog.findViewById(R.id.ediTxtMecPhone);
                ediTextMecCharges = dialog.findViewById(R.id.ediTxtMecCharges);
                ediTextMecExp_year = dialog.findViewById(R.id.ediTxtMecExp_Year);
                ediTextMecExpert_In = dialog.findViewById(R.id.ediTxtMecExpert_In);
                ediTextMecAddress = dialog.findViewById(R.id.ediTxtMecAddress);
                ediTextMecPassword = dialog.findViewById(R.id.ediTxtMecPassword);

                btnAddMechanic = dialog.findViewById(R.id.btnAddMec);

                btnAddMechanic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String mec_name = ediTextMecName.getText().toString();
                        String mec_email = ediTextMecEmail.getText().toString();
                        String mec_phone = ediTextMecPhone.getText().toString();
                        String mec_charges = ediTextMecCharges.getText().toString();
                        String mec_exp_year = ediTextMecExp_year.getText().toString();
                        String mec_expert_in = ediTextMecExpert_In.getText().toString();
                        String mec_address = ediTextMecAddress.getText().toString();
                        String mec_password = ediTextMecPassword.getText().toString();

                        String mobileRegex = "[7-9][0-9]{9}";//First no. may be(7 or 8 or 9)and rest 9 no. can be any no.
                        Matcher mobileMatcher;
                        Pattern mobilePattern = Pattern.compile(mobileRegex);
                        mobileMatcher = mobilePattern.matcher(mec_phone);


                        if(mec_name.trim().isEmpty()){
                            ediTextMecName.setError("Mechanic name is required.");
                            ediTextMecName.requestFocus();
                            return;
                        } else if (mec_email.trim().isEmpty()) {
                            ediTextMecEmail.setError("Email is required.");
                            ediTextMecEmail.requestFocus();
                            return;
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(mec_email).matches()) {
                            ediTextMecEmail.setError("Valid Email required");
                            ediTextMecEmail.requestFocus();
                            return;
                        }else if(mec_phone.trim().isEmpty()){
                            ediTextMecPhone.setError("Mobile number is required");
                            ediTextMecPhone.requestFocus();
                            return;
                        } else if (!mobileMatcher.find()) {
                            ediTextMecPhone.setError("Mobile no. is not valid");
                            ediTextMecPhone.requestFocus();
                            return;
                        }else if(mec_charges.trim().isEmpty()){
                            ediTextMecCharges.setError("Charges is required");
                            ediTextMecCharges.requestFocus();
                            return;
                        }else if(mec_exp_year.trim().isEmpty()){
                            ediTextMecExp_year.setError("Experiences is required");
                            ediTextMecExp_year.requestFocus();
                            return;
                        }else if (mec_expert_in.trim().isEmpty()){
                            ediTextMecExpert_In.setError("Expertises is required");
                            ediTextMecExpert_In.requestFocus();
                            return;
                        }else if (mec_address.trim().isEmpty()){
                            ediTextMecAddress.setError("Address is required");
                            ediTextMecAddress.requestFocus();
                            return;
                        }else if (mec_password.isEmpty()) {
                            ediTextMecPassword.setError("Password required");
                            ediTextMecPassword.requestFocus();
                            return;
                        } else if (mec_password.length() < 5) {
                            ediTextMecPassword.setError("At least six digit pass required");
                            ediTextMecPassword.requestFocus();
                        }else {
                            mec_Id = mec_phone;
                            addMechanic(mec_name,mec_email,mec_phone,mec_charges,mec_exp_year,mec_expert_in,mec_address,mec_password);

                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });
    }
// ====================== Floating Button Add Mechanics End here ======================================




// ===================== Method called addMechanic for adding mechanic ================================
    private void addMechanic(String mec_name, String mec_email, String mec_phone, String mec_charges, String mec_exp_year, String mec_expert_in, String mec_address, String mec_password) {
        MechanicsModel mechanicsModel = new MechanicsModel(mec_name,mec_email,mec_phone,mec_charges,mec_exp_year,mec_expert_in,mec_address,mec_password);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(mec_Id).setValue(mechanicsModel);
                Toast.makeText(AddMechanics.this, "Mechanics Added Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddMechanics.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
// ===================== Method called addMechanic for adding mechanic =================================



    @Override
    protected void onStart() {
        super.onStart();
        mechanicsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mechanicsAdapter.stopListening();
    }
}