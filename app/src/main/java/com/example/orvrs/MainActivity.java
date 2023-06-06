package com.example.orvrs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    //variables
    ImageView imgView;
    TextView txtView1,txtView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imgView);
        txtView1 = findViewById(R.id.txtView1);
        txtView2 = findViewById(R.id.txtView2);


        Animation move = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_car_animation);
        Animation alpha = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_txt_animation);
        imgView.startAnimation(move);
        txtView1.startAnimation(alpha);
        txtView2.startAnimation(alpha);
        getSupportActionBar().hide();

        Intent iHome = new Intent(MainActivity.this, LoginScreen.class);

//        iHome.putExtra("title","Home");  -> for passing data

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(iHome);
                finish();
            }
        }, 7000);


    }
}