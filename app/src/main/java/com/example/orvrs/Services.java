package com.example.orvrs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Services extends AppCompatActivity {

    RecyclerView recyclerView;
    ServicesAdapter servicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        getSupportActionBar().setTitle("Our Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewServices);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ServicesModel> options =
                new FirebaseRecyclerOptions.Builder<ServicesModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Services"), ServicesModel.class)
                        .build();


        servicesAdapter = new ServicesAdapter(options);
        recyclerView.setAdapter(servicesAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        servicesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        servicesAdapter.stopListening();
    }
}