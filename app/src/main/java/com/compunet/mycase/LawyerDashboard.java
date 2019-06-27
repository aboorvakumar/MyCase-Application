package com.compunet.mycase;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class LawyerDashboard extends AppCompatActivity {



    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.one,R.drawable.three};
   // Button send;
    LinearLayout btn_managecase,reminder,btn_addcase,btn_addclient,btn_send,btn_notification,btn_manageclient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyerdashboard);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        btn_manageclient = (LinearLayout)  findViewById(R.id.btn_manageclient);
        btn_manageclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mco = new Intent(LawyerDashboard.this,ManageClient.class);
                startActivity(mco);
            }
        });

        btn_notification = (LinearLayout) findViewById(R.id.btn_notification);
        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent not = new Intent(LawyerDashboard.this,ViewNotificationAdvocate.class);
                startActivity(not);
            }
        });

        btn_send = (LinearLayout) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ff = new Intent(LawyerDashboard.this,Send.class);
                startActivity(ff);
            }
        });

        btn_managecase = (LinearLayout)findViewById(R.id.btn_managecase);
        btn_managecase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manage = new Intent(LawyerDashboard.this,ManageCase.class);
                startActivity(manage);

                managecaseVolley();
            }
        });

        reminder = (LinearLayout)findViewById(R.id.reminder);
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rem = new Intent(LawyerDashboard.this,AlarmMe.class);
                startActivity(rem);
            }
        });
        btn_addcase = (LinearLayout)findViewById(R.id.btn_addcase);
        btn_addcase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rem = new Intent(LawyerDashboard.this,AddCases.class);
                startActivity(rem);
            }
        });

        btn_addclient = (LinearLayout)findViewById(R.id.btn_addclient);
        btn_addclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(LawyerDashboard.this,AddClient.class);
                startActivity(add);
            }
        });

    }
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
   /* @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LawyerDashboard.super.onBackPressed();
                        finish();
                        *//*Intent intent = new Intent(LawyerDashboard.this, SignIn.class);
                        startActivity(intent);
                        finish();*//*
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
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation,menu);
        return  true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                editor.clear();
                editor.commit();
                startActivity(new Intent(this,SignIn.class));
                break;

        }
        return true;
       /* editor.clear();
        editor.commit();

        Intent intent = new Intent(LawyerDashboard.this, SignIn.class);
        startActivity(intent);
        finish();
        return  true;*/
    }

    private void managecaseVolley() {

/*
        RequestQueue queue = Volley.newRequestQueue(this);
        String url_managecase = "http://122.165.147.48:8888/advocate/public/caseDetails";
        StringRequest mcd = new StringRequest(Request.Method.POST, url_managecase,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response",response);

                        try {
                            JSONObject objaddclient = new JSONObject(response);




                            if (objaddclient.getString("result").equals("success") ){
                                Intent cli = new Intent(LawyerDashboard.this,LawyerDashboard.class);
                                startActivity(cli);
                                finish();
                                Toast.makeText(getApplicationContext()," successfully Added",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();
                            }





                        } catch (Throwable t) {
                            Log.d("My App", "Could not parse malformed JSON: \"" + response + "\"");

                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "Error :" + error.toString());
                        //Toast.makeText(getApplicationContext(),"test2",Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("client_name",txt_client_name.getText().toString() );
                params.put("client_email", txt_email.getText().toString());
                params.put("client_mobie_no", txt_mobileno.getText().toString());
                params.put("client_state", state.getText().toString());
                params.put("client_city", city.getText().toString());
                params.put("client_pincode", pincode.getText().toString());
                params.put("client_address", txt_address.getText().toString());


                return params;
            }
        };
        queue.add(mcd);*/
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };






}
