package com.example.orvrs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Mechanics extends AppCompatActivity {

    RecyclerView recyclerViewMechanics;
    MechanicsAdapter mechanicsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics);

        getSupportActionBar().setTitle("Mechanics For Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerViewMechanics = findViewById(R.id.recyclerViewMechanics);
        recyclerViewMechanics.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<MechanicsModel> options =
                new FirebaseRecyclerOptions.Builder<MechanicsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Mechanics"), MechanicsModel.class)
                        .build();


        mechanicsAdapter = new MechanicsAdapter(options);
        recyclerViewMechanics.setAdapter(mechanicsAdapter);


    }

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