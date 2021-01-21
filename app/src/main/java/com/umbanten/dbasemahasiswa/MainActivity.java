package com.umbanten.dbasemahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    //Lama delay dengan satuan ms
    private static final int lamaDelay=3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Atur layar supaya ga ada judul ActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        //Untuk tampilan Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Delay activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        },lamaDelay);
    }
}