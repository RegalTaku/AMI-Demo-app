package com.churchapp.ami;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    ImageView splashImage;
    TextView splashTextView;
    public final static int blinkTime = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        splashImage = findViewById(R.id.splashImage);
        splashTextView = findViewById(R.id.splashTextView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, blinkTime);

        Animation blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink);
        Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        splashImage.startAnimation(fadeInAnimation);
        splashTextView.startAnimation(blinkAnimation);
        splashTextView.startAnimation(bounceAnimation);
    }
}
