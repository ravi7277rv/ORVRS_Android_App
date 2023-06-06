package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {

    private Toolbar toolBar;
    private Button btnAddMechanic,btnViewUser,btnAddServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnAddMechanic = findViewById(R.id.btnAddMechanic);
        btnAddServices = findViewById(R.id.btnAddServices);
        btnViewUser = findViewById(R.id.btnViewUsers);




// ========================= View Users  btnViewUser Start here ========================================
        btnViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(AdminDashboard.this,ViewUsers.class));
            }
        });
// ========================= View Users  btnViewUser Start here ========================================




// ========================= Add Services  btnAddServices Start here ===================================
        btnAddServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this,AddServices.class));
            }
        });
// ========================= Add Services  btnAddServices End here =====================================




// ========================= Add Mechanic btnAddMechanic Start here =====================================
        btnAddMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this,AddMechanics.class));
            }
        });

// ========================= Add Mechanic btnAddMechanic End here =====================================




// ========================= Admin ToolBar Section Start here  ===========================================
            toolBar = findViewById(R.id.adminToolBar);
            setSupportActionBar(toolBar);
            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle("Admin Tool Bar");
            }
            toolBar.setTitle("AdminDashboard");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
//            if (itemId == R.id.admMec) {
//                Toast.makeText(this, "created  new file", Toast.LENGTH_LONG).show();
//            } else if (itemId == R.id.admRequest) {
//                Toast.makeText(this, "Open file", Toast.LENGTH_LONG).show();
//            } else if (itemId == R.id.admServices) {
//                Toast.makeText(this, "Saved as file", Toast.LENGTH_LONG).show();
//            } else if (itemId == R.id.admVehicles) {
//                Toast.makeText(this, "Saved as file", Toast.LENGTH_LONG).show();
//            } else
                if (itemId == R.id.admLogOut) {

                Toast.makeText(this, "Admin Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminDashboard.this, AdminLogin.class);

                //Clear stack to prevent user coming back to UserProfileActivity on pressing back button after Logging out
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        return super.onOptionsItemSelected(item);
    }
// ========================= Admin ToolBar Section End here  ===========================================



}