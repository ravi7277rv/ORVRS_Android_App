package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestService extends AppCompatActivity {

    private TextView txtUserEmail,txtUserPhone,txtMechanicEmail,txtMechanicPhone;
    private EditText ediProblem,ediLocation;
    private FirebaseAuth fAuth;
    private String user_email,user_phone;
    private Button btnSubmitRequest;
    private String serviceRequestId;
    private String status = "Incomplete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);

        getSupportActionBar().setTitle("Request Service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtUserEmail = findViewById(R.id.txt_user_email);
        txtUserPhone = findViewById(R.id.txt_user_phone);
        txtMechanicEmail = findViewById(R.id.txt_mec_email);
        txtMechanicPhone = findViewById(R.id.txt_mec_phone);
        ediProblem = findViewById(R.id.edi_problem);
        ediLocation = findViewById(R.id.edi_location);
        btnSubmitRequest = findViewById(R.id.btn_submit_request);


        //Getting mechanic data from the  intent data which passed form the MechanicAdapter
        Intent fromAdapter = getIntent();
        String mechanic_email = fromAdapter.getStringExtra("email");
        String mechanic_phone = fromAdapter.getStringExtra("phone");

        //Setting mechanic data to the respective view
        txtMechanicEmail.setText(mechanic_email);
        txtMechanicPhone.setText(mechanic_phone);


        //now we are fetching the user data form the database registered user
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }else {

            String userId = firebaseUser.getUid();
            DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("RegisteredUser");
            referenceUser.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    UserModel readWriteUserDetails = snapshot.getValue(UserModel.class);

                    if(readWriteUserDetails != null){

                        //Getting the data form the database
                        user_email = readWriteUserDetails.getEmail();
                        user_phone = readWriteUserDetails.getMobile();

                        //Setting the data to the respective fields
                        txtUserEmail.setText(user_email);
                        txtUserPhone.setText(user_phone);

                    }else {
                        Toast.makeText(RequestService.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        //When Submit Request Button will be clicked
        btnSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String problem = ediProblem.getText().toString();
                String location = ediLocation.getText().toString();

                if(problem.isEmpty()){
                    ediProblem.setError("Problem should be entered before submitting request");
                    ediProblem.requestFocus();
                }else if(location.isEmpty()){
                    ediLocation.setError("Location should be entered properly!");
                    ediLocation.requestFocus();
                }else{
                    requestService(user_email,user_phone,mechanic_email,mechanic_phone,problem,location,status);

                }
            }
        });

    }

    private void requestService(String user_email, String user_phone, String mechanic_email, String mechanic_phone, String problem, String location,String status) {

        fAuth = FirebaseAuth.getInstance();
        serviceRequestId = mechanic_phone;
        RequestServiceModel requestServiceModel = new RequestServiceModel(user_email,user_phone,mechanic_email,mechanic_phone,problem,location,status);

        DatabaseReference referenceRequestService = FirebaseDatabase.getInstance().getReference("RequestServices");
        referenceRequestService.child(serviceRequestId).setValue(requestServiceModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(RequestService.this, "Request Submitted Successfully", Toast.LENGTH_SHORT).show();



                    Intent intent = new Intent(RequestService.this,UserDashboard.class);
                    //To prevent User from returning back to Register Activity on pressing back button after registration
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // to close Register Activity

                }else {
                    Toast.makeText(RequestService.this, "Request Submission Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}