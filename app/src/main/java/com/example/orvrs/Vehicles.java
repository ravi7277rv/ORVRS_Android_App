package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Vehicles extends AppCompatActivity {

    private RecyclerView recyclerViewVehicle;
    private FloatingActionButton floatingBtnAddVehicles;
    private FirebaseAuth  mAuth;
    private DatabaseReference databaseReference;
    private String vehicleId;
    private VehicleAdapter vehicleAdapter;
    private String email,mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);

        getSupportActionBar().setTitle("Vehicles");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



// ========================== Getting the vehicle from the data base ============================================================
        recyclerViewVehicle = findViewById(R.id.recyclerViewVehicle);
        floatingBtnAddVehicles = findViewById(R.id.floatingBtnAddVehicle);
        recyclerViewVehicle.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<VehicleModel> options =
                new FirebaseRecyclerOptions.Builder<VehicleModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Vehicles"), VehicleModel.class)
                        .build();
        vehicleAdapter = new VehicleAdapter(options);
        recyclerViewVehicle.setAdapter(vehicleAdapter);

// ============================================= Getting Vehicle End here ======================================================


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = mAuth.getCurrentUser();

        if(fUser == null){
            Toast.makeText(this, "Something went Wrong!", Toast.LENGTH_SHORT).show();
        }else {
            String userId = fUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("RegisteredUser");
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel readWriteUserDetails = snapshot.getValue(UserModel.class);
                    if(readWriteUserDetails != null){
                        email = readWriteUserDetails.email; //firebaseUser.getEmail();
                        mobile = readWriteUserDetails.mobile;
                    }else {
                        Toast.makeText(Vehicles.this, "User Doesn't Exist!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

// ============================  Adding Vehicle Start here ======================================================================
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicles");
        floatingBtnAddVehicles.setOnClickListener(new View.OnClickListener() {

            EditText edtOwnerName,edtVehicleNumber,edtVehicleModel,edtVehicleChassisNo;
            Button btnAddVehicle;
            @Override
            public void onClick(View view) {


                //Creating the dialog when click on FloatingAction Button
                Dialog dialog = new Dialog(Vehicles.this);
                dialog.setContentView(R.layout.add_vehicle_dialog);

                edtOwnerName = dialog.findViewById(R.id.ediVehicle_Owner_name);
                edtVehicleNumber = dialog.findViewById(R.id.ediVehicle_number);
                edtVehicleModel = dialog.findViewById(R.id.ediVehicle_Model);
                edtVehicleChassisNo = dialog.findViewById(R.id.ediVehicle_Chassis_no);

                btnAddVehicle = dialog.findViewById(R.id.btnAddVehicle);

                btnAddVehicle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String owner_name = edtOwnerName.getText().toString();
                        String vehicle_number = edtVehicleNumber.getText().toString();
                        String vehicle_model = edtVehicleModel.getText().toString();
                        String vehicle_chassis = edtVehicleChassisNo.getText().toString();

                        if(owner_name.isEmpty()){
                            edtOwnerName.setError("OwnerName is required");
                            edtOwnerName.requestFocus();
                        }else if(vehicle_number.isEmpty()){
                            edtVehicleNumber.setError("Vehicle number is required");
                            edtVehicleNumber.requestFocus();
                        }else if(vehicle_model.isEmpty()){
                            edtVehicleModel.setError("Vehicle model number is required");
                            edtVehicleModel.requestFocus();
                        }else if(vehicle_chassis.isEmpty()){
                            edtVehicleChassisNo.setError("Vehicle chassis number is required");
                            edtVehicleChassisNo.requestFocus();
                        }else{
                            vehicleId = mobile;
                            addVehicle(owner_name,vehicle_number,vehicle_model,vehicle_chassis,email,mobile);

                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();

           }
        });
    }
// =====================================================================================================================================
//=========================== addVehicle is called here is implementation Start here ==============================================
    private void addVehicle(String owner_name, String vehicle_number, String vehicle_model, String vehicle_chassis,String email,String mobile) {

        VehicleModel vehicleModel = new VehicleModel(owner_name,vehicle_number,vehicle_model,vehicle_chassis,email,mobile);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                databaseReference.child(vehicleId).setValue(vehicleModel);

                Toast.makeText(Vehicles.this, "Vehicle Added Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Vehicles.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }
//=========================== addVehicle is called here is implementation End here ==============================================

    @Override
    protected void onStart() {
        super.onStart();
        vehicleAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vehicleAdapter.stopListening();
    }


}