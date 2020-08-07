package com.example.findmyblod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectionScreenActivity extends AppCompatActivity {
    private Button mFindBlood, mDonateBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);



        mFindBlood = findViewById(R.id.buttonFindBlood);
        mFindBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsActivety = new Intent(SelectionScreenActivity.this,MainActivity.class);
                startActivity(mapsActivety);
            }
        });

        mDonateBlood = findViewById(R.id.buttonDonateBlood);
        mDonateBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donateBloodActivityObject = new Intent(SelectionScreenActivity.this,DonateBloodActivity.class);
                startActivity(donateBloodActivityObject);
            }
        });
    }
}
