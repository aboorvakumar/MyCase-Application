package com.compunet.mycase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientSignin extends AppCompatActivity  {


    private static final Object CHANNEL_NAME = "Simplified Coding" ;
    private static final Object CHANNEL_ID = "simplified_coding";
    private static final Object CHANNEL_DESC = "Android Push Notification Tutorial";
    AwesomeValidation awesomeValidation;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    String  url = "http://demo.compunet.in/advocateApi/public/login";
    EditText client_mail,client_passwd;
    Button client_signIn;
    String  flag = String.valueOf(2);
    String token_value;
    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientsignin);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SignIn");

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        client_mail = (EditText)findViewById(R.id.client_mail);
        client_passwd = (EditText) findViewById(R.id.client_passwd);
        client_signIn = (Button)findViewById(R.id.client_signIn);



        client_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientVolley();
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.d("Answer", "getInstanceId failed", task.getException());
                                    return;
                                }


                                String token = task.getResult().getToken();


                               // String msg = getString(R.string.msg_token_fmt, token);
                                token_value = token;
                                editor.putString("token",token_value);
                                Log.d("Answer", token_value);
                                //Toast.makeText(ClientSignin.this, token_value, Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });
    }







    private void clientVolley() {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);



                            if (obj.getString("result").equals("success") ){


                                        Intent cli = new Intent(ClientSignin.this,MainActivity.class);
                                        startActivity(cli);
                                        finish();
                                        //Toast.makeText(getApplicationContext(),"Login successfully!!!",Toast.LENGTH_LONG).show();

                                        editor.putString("username",client_mail.getText().toString());
                                        editor.putString("password",client_passwd.getText().toString());
                                        editor.putString("status",flag);
                                        editor.commit();

                                        //Retrive the value

                                        String retrive_client = prefs.getString("status", null);
                                        Log.d("retrive_client",retrive_client);
                                    }

                                    else {
                                        Toast.makeText(getApplicationContext(),"Email or Password incorrect",Toast.LENGTH_LONG).show();
                                    }

                            JSONObject A = obj.getJSONObject("user");
                            String B = A.getString("id");
                            Log.d("aboorva",B);

                            setDefaults("id",B,getApplicationContext());

                        } catch (Throwable t) {
                            Log.d("My App", "Could not parse malformed JSON: \"" + response + "\"");

                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error.Response", "Error :" + error.toString());
                        //Toast.makeText(getApplicationContext(),"test2",Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username",client_mail.getText().toString() );
                params.put("password", client_passwd.getText().toString());
                params.put("token",token_value);
                params.put("status", flag);

                return params;
            }
        };
        queue.add(postRequest);
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ClientSignin.this, SignIn.class);
        startActivity(intent);
        finish();
    }


}
