package com.example.orvrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MechanicDashboard extends AppCompatActivity {

    private Toolbar toolBarMec;
    private TextView txtMecName;
    private CardView cardMecProfile,cardMecServices,cardMecServRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_dashboard);

        txtMecName = findViewById(R.id.txtMecName);
        txtMecName.setText(Prevalent.currentOnlineMechanic.getName());

        cardMecProfile = findViewById(R.id.cardMecProfile);
        cardMecServices = findViewById(R.id.cardMecServices);
        cardMecServRequest = findViewById(R.id.cardMecServReq);


// ================================ CARD VIEW FOR MECHANIC PROFILE START HERE =========================================
        cardMecProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MechanicDashboard.this,MechanicProfile.class));
            }
        });
// ================================ CARD VIEW FOR MECHANIC PROFILE END HERE =========================================




// ================================ CARD VIEW FOR MECHANIC SERVICES START HERE =========================================
        cardMecServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MechanicDashboard.this,Services.class));
            }
        });
// ================================ CARD VIEW FOR MECHANIC PROFILE END HERE =========================================




// ================================ CARD VIEW FOR MECHANIC SERVICES REQUEST START HERE =========================================
        cardMecServRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
// ================================ CARD VIEW FOR MECHANIC SERVICES REQUEST END HERE =========================================





// ========================= Mechanic ToolBar Section Start here  ===========================================
        toolBarMec = findViewById(R.id.mechanicToolBar);
        setSupportActionBar(toolBarMec);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Admin Tool Bar");
        }
        toolBarMec.setTitle("MechanicDashboard");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.mechanic_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
//        if(itemId == R.id.mecProfile){
//            startActivity(new Intent(MechanicDashboard.this,MechanicProfile.class));
//        }else if (itemId == R.id.reqService) {
//            startActivity(new Intent(MechanicDashboard.this,Services.class));
//        } else if (itemId == R.id.allServices) {
//            Toast.makeText(this, "Open file", Toast.LENGTH_LONG).show();
//        } else
            if (itemId == R.id.mecLogOut) {
            Toast.makeText(this, "Mechanic Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MechanicDashboard.this, MechanicLogin.class);
            //Clear stack to prevent user coming back to UserProfileActivity on pressing back button after Logging out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
// ========================= Mechanic ToolBar Section End here  ===========================================
    }
