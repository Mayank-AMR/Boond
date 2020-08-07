package com.example.findmyblod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "SplashScreen";
    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        CountDownTimer cntDownTimer  = new CountDownTimer(1000,10) {
            private static final String TAG = "SplashScreen";
            @Override
            public void onTick(long millisUntilFinished) {
//                Log.d(TAG, "onTick: ");
//                Toast.makeText(SplashScreen.this,"Hello",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
                Intent selectScreen = new Intent(SplashScreen.this,SelectionScreenActivity.class);
                startActivity(selectScreen);
                finish();

            }
        };
        cntDownTimer.start();
    }
}
