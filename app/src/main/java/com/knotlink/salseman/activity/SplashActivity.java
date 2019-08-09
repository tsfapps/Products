package com.knotlink.salseman.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.knotlink.salseman.R;
import com.knotlink.salseman.storage.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {
    private SharedPrefManager tSharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tSharedPrefManager = new SharedPrefManager(getApplicationContext());
        startHandler();
    }

    public void startHandler()
    {
        Handler tHandler = new Handler();
        tHandler.postDelayed(
                new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                if (tSharedPrefManager.getUserId().equalsIgnoreCase("")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 2000);
    }


}
