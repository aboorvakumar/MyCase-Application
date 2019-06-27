package com.compunet.mycase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


public class MainActivity extends AppCompatActivity {
    CarouselView carouselView;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    LinearLayout btn_quires,btn_reminder,btn_adj,btn_about;

    int[] sampleImages = {R.drawable.one,R.drawable.three};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        btn_about = (LinearLayout)findViewById(R.id.btn_about);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // setup the alert builder

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(getResources().getDrawable(R.drawable.info));
                builder.setTitle("COMING SOON");
                builder.setMessage("This feature will be available soon.");
                // add the buttons
                builder.setPositiveButton("ok", null);
               // builder.setNegativeButton("Cancel", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();


                       /* AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());
                        builder.setIcon(getResources().getDrawable(R.drawable.info));
                        builder.setTitle("COMING SOON");
                        builder.setMessage("This feature will be available soon.");
                        builder.setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {



                                    }
                                });

                        builder.setCancelable(false);
                        AlertDialog alert = builder.create();
                        alert.show();*/

                    }
                });



        btn_quires = (LinearLayout)findViewById(R.id.btn_quires);
        btn_quires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Queries");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                intent.setData(Uri.parse("mailto:actfordev@gmail.com")); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);

            }
        });

        btn_reminder = (LinearLayout)findViewById(R.id.btn_reminder);
        btn_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rem = new Intent(MainActivity.this,AlarmMe.class);
                startActivity(rem);
            }
        });

        btn_reminder = (LinearLayout)findViewById(R.id.btn_reminder);
        btn_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rem = new Intent(MainActivity.this,AlarmMe.class);
                startActivity(rem);
            }
        });
        btn_adj= (LinearLayout)findViewById(R.id.btn_adj);
        btn_adj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adj = new Intent(MainActivity.this,Adjournment.class);
                startActivity(adj);
            }
        });
    }
   /* public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }*/
    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })

                .setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_client,menu);
        return  true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){



            case R.id.logout:
                editor.clear();
                editor.commit();
                startActivity(new Intent(this,ClientSignin.class));
                break;

        }
        return true;

    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

}
