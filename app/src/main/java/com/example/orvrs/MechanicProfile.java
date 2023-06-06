package com.example.orvrs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MechanicProfile extends AppCompatActivity {

    private TextView txtMN,txtMPN,txtMPP,txtMPE,txtMPA,txtMPC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_profile);

        getSupportActionBar().setTitle("Mechanic Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtMN = findViewById(R.id.txtMN);
        txtMPN = findViewById(R.id.txtMecPN);
        txtMPP = findViewById(R.id.txtMecPP);
        txtMPE = findViewById(R.id.txtMecPE);
        txtMPA = findViewById(R.id.txtMecPA);
        txtMPC = findViewById(R.id.txtMecPC);

        txtMN.setText(Prevalent.currentOnlineMechanic.getName());
        txtMPN.setText(Prevalent.currentOnlineMechanic.getName());
        txtMPP.setText(Prevalent.currentOnlineMechanic.getPhone());
        txtMPE.setText(Prevalent.currentOnlineMechanic.getEmail());
        txtMPA.setText(Prevalent.currentOnlineMechanic.getAddress());
        txtMPC.setText(Prevalent.currentOnlineMechanic.getCharges());


    }
}