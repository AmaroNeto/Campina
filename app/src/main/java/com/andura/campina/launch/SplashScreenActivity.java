package com.andura.campina.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andura.campina.R;
import com.andura.campina.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by amaro on 03/12/16.
 */

public class SplashScreenActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {

                Intent it = new Intent();
                it.setClass(SplashScreenActivity.this, MainActivity.class);
                startActivity(it);

                finish();
            }
        }, 2000);

    }

}
