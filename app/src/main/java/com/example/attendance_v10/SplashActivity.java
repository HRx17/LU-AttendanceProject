package com.example.attendance_v10;

import androidx.appcompat.app.AppCompatActivity;

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

import java.awt.font.TextAttribute;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    Button tosignup,tologin;
    TextView text;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        text = findViewById(R.id.splash_txt);
        progressBar = findViewById(R.id.pg_bar);
        tologin = findViewById(R.id.admin);
        tosignup = findViewById(R.id.student);

        tosignup.setVisibility(View.GONE);
        tologin.setVisibility(View.GONE);

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
                tologin.setVisibility(View.VISIBLE);
                tosignup.clearAnimation();
                tosignup.startAnimation(animFadeIn);
                tologin.clearAnimation();
                tologin.startAnimation(animFadeIn);
                animFadeOut.reset();
                tosignup.clearAnimation();
                tosignup.startAnimation(animFadeOut);
                tologin.clearAnimation();
                tologin.startAnimation(animFadeOut);
                animFadeIn.reset();
                tosignup.clearAnimation();
                tosignup.startAnimation(animFadeIn);
                tologin.clearAnimation();
                tologin.startAnimation(animFadeIn);
            }
        },3000);
    }
}