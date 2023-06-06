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
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    private Toolbar toolBar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ImageSlider imageSlider,imageSlider2;
    private CardView cardLayoutUserProfile,cardLayoutMechanics,cardLayoutServices,cardLayoutVehicles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

//================  USER MECHANIC SERVICE VEHICLE LAYOUT SECTION START HERE ==============================

        cardLayoutUserProfile = findViewById(R.id.cardLayout_profile);
        cardLayoutMechanics = findViewById(R.id.cardLayout_mechanics);
        cardLayoutServices = findViewById(R.id.cardLayout_services);
        cardLayoutVehicles = findViewById(R.id.cardLayout_vehicles);

        //UserProfile Layout Opening here
        cardLayoutUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboard.this,UserProfile.class));
            }
        });


        //Mechanics Layout Opening here
        cardLayoutMechanics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboard.this,Mechanics.class));
            }
        });


        //Services Layout Opening here
        cardLayoutServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboard.this,Services.class));
            }
        });


        //Vehicles Layout Opening here
        cardLayoutVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboard.this,Vehicles.class));
            }
        });

// ===================================== CARD LAYOUT SECTION END HERE =======================================


// =============================  IMAGE SLIDER FOR MECHANICS VIEW START HERE =================================
        imageSlider = findViewById(R.id.imageSlider);

        ArrayList<SlideModel> slideModels = new ArrayList<SlideModel>();

        slideModels.add(new SlideModel(R.drawable.mec_1,"Mechanic1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mec_2,"Mechanic1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mec_3,"Mechanic1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mec_4,"Mechanic1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mec_5,"Mechanic1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mec_6,"Mechanic1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mec_7,"Mechanic1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mec_8,"Mechanic1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mec_9,"Mechanic1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mec_10,"Mechanic1", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);


//========================= IMAGE SLIDER FOR MECHANICS VIEW END HERE =============================


        imageSlider2 = findViewById(R.id.imageSlider2);

        ArrayList<SlideModel> slideModels2 = new ArrayList<>();

        slideModels2.add(new SlideModel(R.drawable.p1,"Tyre and Fuel",ScaleTypes.FIT));
        slideModels2.add(new SlideModel(R.drawable.p2,ScaleTypes.FIT));
        slideModels2.add(new SlideModel(R.drawable.p3,ScaleTypes.FIT));
        slideModels2.add(new SlideModel(R.drawable.p4,ScaleTypes.FIT));
        slideModels2.add(new SlideModel(R.drawable.p5,ScaleTypes.FIT));
        slideModels2.add(new SlideModel(R.drawable.p6,ScaleTypes.FIT));

        imageSlider2.setImageList(slideModels2,ScaleTypes.FIT);

// =======================  IMAGE SLIDER FOR SPARE PARTS START HERE =====================



// =========  IMAGE SLIDER FOR SPARE PARTS END HERE =====================





// =========================================  TOOLBAR SECTION  ========================================
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) { // this line will check whether you have set toolbar or not
           // getSupportActionBar().setDisplayHomeAsUpEnabled(true); // this will show back button
            getSupportActionBar().setTitle("MY ToolBar");
        }
        toolBar.setTitle("UserDashboard");
        //toolBar.setSubtitle("Sub Title");
    }

    //setting option_menu  in toolbar   three dot which represent list of option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
//        if (itemId == R.id.opt_new) {
//            Toast.makeText(this, "created  new file", Toast.LENGTH_LONG).show();
//        } else if (itemId == R.id.opt_open) {
//            Toast.makeText(this, "Open file", Toast.LENGTH_LONG).show();
//        } else if (itemId == R.id.opt_save) {
//            Toast.makeText(this, "Saved as file", Toast.LENGTH_LONG).show();
//        } else if (itemId == android.R.id.home) {
//            super.onBackPressed();
//            Toast.makeText(this, "Home button", Toast.LENGTH_LONG).show();
//        } else
          if (itemId == R.id.opt_logout) {
            mAuth.signOut();
            Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserDashboard.this, LoginScreen.class);

            //Clear stack to prevent user coming back to UserProfileActivity on pressing back button after Logging out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
// ====================================================== TOOLBAR SECTION END HERE =====================================================
    }


}