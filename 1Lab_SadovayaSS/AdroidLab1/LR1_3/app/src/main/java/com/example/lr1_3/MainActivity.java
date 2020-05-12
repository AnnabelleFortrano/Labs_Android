package com.example.lr1_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        new Thread() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    finish();
                    Intent i = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(i);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
