package com.example.attendance_v10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.awt.font.TextAttribute;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    Button tosignup,admin;
    ImageView text;
    ProgressBar progressBar;
    FirebaseAuth auth;
    String tokn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        text = findViewById(R.id.splash_txt);
        progressBar = findViewById(R.id.pg_bar);
        admin = findViewById(R.id.admin);
        tosignup = findViewById(R.id.student);

        tosignup.setVisibility(View.GONE);
        admin.setVisibility(View.GONE);

        text.setVisibility(View.GONE);
        Animation animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                text.setVisibility(View.VISIBLE);
                animFadeIn.reset();
                text.clearAnimation();
                text.startAnimation(animFadeIn);
                tosignup.setVisibility(View.VISIBLE);
                admin.setVisibility(View.VISIBLE);
                tosignup.clearAnimation();
                tosignup.startAnimation(animFadeIn);
                admin.clearAnimation();
                admin.startAnimation(animFadeIn);
                animFadeOut.reset();
                tosignup.clearAnimation();
                tosignup.startAnimation(animFadeOut);
                admin.clearAnimation();
                admin.startAnimation(animFadeOut);
                animFadeIn.reset();
                tosignup.clearAnimation();
                tosignup.startAnimation(animFadeIn);
                admin.clearAnimation();
                admin.startAnimation(animFadeIn);
            }
        },2600);

        auth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("token",0);
        tokn = sharedPreferences.getString("token",null);

        if(auth.getCurrentUser() != null && tokn.equals("20216")){
            startActivity(new Intent(SplashActivity.this, AdminMain.class));
            Toast.makeText(SplashActivity.this,"Already Logged in, Please wait!",Toast.LENGTH_SHORT).show();
            finish();
        }

        else if(auth.getCurrentUser() != null){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            Toast.makeText(SplashActivity.this,"Already Logged in, Please wait!",Toast.LENGTH_SHORT).show();
            finish();
        }

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this,Admin_Login.class);
                startActivity(intent);
                finish();
            }
        });
        tosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this,Registeration.class);
                startActivity(intent);
                finish();
            }
        });
    }
}