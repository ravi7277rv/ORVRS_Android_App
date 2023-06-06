package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
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

public class AddServices extends AppCompatActivity {

    private RecyclerView recyclerService;
    private ServicesAdapter servicesAdapter;
    private FloatingActionButton floatingActionButtonAddService;
    private String service_Id;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);

        getSupportActionBar().setTitle("Our Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Services");

// ======================= Code for showing the Services Start here ========================================
        recyclerService = findViewById(R.id.recyclerService);
        recyclerService.setLayoutManager(new LinearLayoutManager(AddServices.this));

        FirebaseRecyclerOptions<ServicesModel> options =
                new FirebaseRecyclerOptions.Builder<ServicesModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Services"), ServicesModel.class)
                        .build();


        servicesAdapter = new ServicesAdapter(options);
        recyclerService.setAdapter(servicesAdapter);

// ======================= Code for showing Services End Here ======================================


// ====================== Floating Button Add Services Start here ==================================
        floatingActionButtonAddService = findViewById(R.id.floatingAddService);

        floatingActionButtonAddService.setOnClickListener(new View.OnClickListener() {

            EditText ediTxtServiceName,ediTxtServicePrice,ediTxtServiceDesc,ediTxtServicePurl;

            Button btnAddService;

            @Override
            public void onClick(View view) {
                Toast.makeText(AddServices.this, "inside onClick method", Toast.LENGTH_SHORT).show();
                Dialog dialog = new Dialog(AddServices.this);
                dialog.setContentView(R.layout.add_services_dialog);

                ediTxtServiceName = dialog.findViewById(R.id.ediTxtServiceName);
                ediTxtServicePrice = dialog.findViewById(R.id.ediTxtServicePrice);
                ediTxtServiceDesc = dialog.findViewById(R.id.ediTxtServiceDesc);
                ediTxtServicePurl = dialog.findViewById(R.id.ediTxtServicePurl);

                btnAddService = dialog.findViewById(R.id.btnAddServ);

                btnAddService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String serv_name = ediTxtServiceName.getText().toString();
                        String serv_price = ediTxtServicePrice.getText().toString();
                        String serv_desc = ediTxtServiceDesc.getText().toString();
                        String serv_purl = ediTxtServicePurl.getText().toString();


                        if(serv_name.trim().isEmpty()){
                            ediTxtServiceName.setError("Service name is required");
                            ediTxtServiceName.requestFocus();
                            return;
                        }else if(serv_price.trim().isEmpty()){
                            ediTxtServicePrice.setError("Price is required");
                            ediTxtServicePrice.requestFocus();
                            return;
                        }else if (serv_desc.trim().isEmpty()){
                            ediTxtServiceDesc.setError("Description of service is required");
                            ediTxtServiceDesc.requestFocus();
                            return;
                        }else if (serv_purl.trim().isEmpty()){
                            ediTxtServicePurl.setError("Service Image Url is required");
                            ediTxtServicePurl.requestFocus();
                            return;
                        }else {

                            service_Id = serv_name+serv_price;
                            addService(serv_name,serv_price,serv_desc,serv_purl);

                            dialog.dismiss();
                        }

                    }
                });
                dialog.show();
            }
        });

// ====================== Floating Button Add Services End here ==================================



    }
// ====================== Add Service Method Start here ===========================================
    private void addService(String serv_name, String serv_price, String serv_desc, String serv_purl) {

        ServicesModel servicesModel = new ServicesModel(serv_purl,serv_name,serv_price,serv_desc);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(service_Id).setValue(servicesModel);
                Toast.makeText(AddServices.this, "Services Added Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddServices.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
// ===================== Add Service Method End here ===============================================



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