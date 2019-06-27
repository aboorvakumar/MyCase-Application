package com.compunet.mycase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.WindowManager;

public class Loading  extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String status,Retrive;
    String storedUsername;
    String storedPassword;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.loading);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        status = prefs.getString("status", "");
        storedUsername = prefs.getString("username", "");
        storedPassword = prefs.getString("password", "");

        Log.d("status",status);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },3000);


        if (status.equals("1" )){
            if (!storedUsername.equalsIgnoreCase("") && !storedPassword.equalsIgnoreCase("")){
                intent = new Intent(this,LawyerDashboard.class);
                startActivity(intent);
                finish();
            }else {
                intent = new Intent(this,SignIn.class);
                startActivity(intent);
            }


        }
        else if (status.equals("2")){

            if (!storedUsername.equalsIgnoreCase("") && !storedPassword.equalsIgnoreCase("")){
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                intent = new Intent(this,ClientSignin.class);
                startActivity(intent);
            }


        }
        else if (status.equals("")){
            Intent intent = new Intent(this,SignIn.class);
            startActivity(intent);
            finish();;
        }




    }

}
