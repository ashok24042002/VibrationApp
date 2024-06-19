package com.example.vibrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveAct1();

                finish();
            }
        }, 2000);
    }
    private void moveAct1(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}