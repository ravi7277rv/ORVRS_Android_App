package com.example.orvrs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ViewUsers extends AppCompatActivity {

    private RecyclerView recyclerUsers;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);


        getSupportActionBar().setTitle("Registered Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerUsers = findViewById(R.id.recyclerViewUser);
        recyclerUsers.setLayoutManager(new LinearLayoutManager(ViewUsers.this));


        FirebaseRecyclerOptions<UserModel> options =
                new FirebaseRecyclerOptions.Builder<UserModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("RegisteredUser"), UserModel.class)
                        .build();


        userAdapter = new UserAdapter(options);
        recyclerUsers.setAdapter(userAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userAdapter.stopListening();
    }
}