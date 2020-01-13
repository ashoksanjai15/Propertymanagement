package com.example.propertymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;
    Button sub;
    Animation frombottom,fromtop;
    ImageView ballon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);

        sub = (Button)findViewById(R.id.sub);
        ballon=(ImageView)findViewById(R.id.ballon);
        frombottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop=AnimationUtils.loadAnimation(this,R.anim.fromtop);
        sub.setAnimation(frombottom);
        ballon.setAnimation(fromtop);
    }

}