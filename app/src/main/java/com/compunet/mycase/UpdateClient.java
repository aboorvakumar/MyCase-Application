package com.compunet.mycase;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateClient  extends AppCompatActivity
{
    String client_id="",client_name="",client_email="",client_mobile="",client_address="",client_state="",client_city="",client_pincode="",client_date="";
    Button update;
    EditText name,email,phone,address,state,city,pincode;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_client);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Client Details");

        Intent intent = getIntent();
        if (null != intent){
            //case_id = intent.getStringExtra("case_id");
            client_id = intent.getStringExtra("client_id");
            id = client_id;
            Log.d("respose",client_id);

            client_name = intent.getStringExtra("client_name");

            client_email = intent.getStringExtra("client_email");

            client_mobile = intent.getStringExtra("client_mobile");


            client_address = intent.getStringExtra("client_address");

            client_state = intent.getStringExtra("client_state");

            client_city = intent.getStringExtra("client_city");

            client_pincode = intent.getStringExtra("client_pincode");

            client_date = intent.getStringExtra("client_date");



            //show the id here
           // Toast.makeText(getApplicationContext(), "id "+client_id , Toast.LENGTH_LONG).show();


        }
        else {

            Toast.makeText(getApplication(),"try Again!!!",Toast.LENGTH_SHORT);
        }

        name = (EditText)findViewById(R.id.cc_name);
        email = (EditText) findViewById(R.id.c_email);
        phone = (EditText) findViewById(R.id.c_phone);
        address = (EditText) findViewById(R.id.c_address);
        state = (EditText) findViewById(R.id.c_state);
        city = (EditText) findViewById(R.id.c_city);
        pincode = (EditText) findViewById(R.id.pincode);

        name.setText(client_name);

        email.setText(client_email);

        phone.setText(client_mobile);

        address.setText(client_address);

        state.setText(client_state);

        city.setText(client_city);

        pincode.setText(client_pincode);

        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientUpdateVolley();
            }
        });






        //name.setText(client_name);


    }

    private void clientUpdateVolley() {


        String url = "http://demo.compunet.in/advocateApi/public/updateClient";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest client_update = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {




                        Log.d("Response",response);

                        try {
                            JSONObject obj = new JSONObject(response);




                            if (obj.getString("result").equals("success") ){
                                Intent upc = new Intent(UpdateClient.this,ManageClient.class);
                                startActivity(upc);
                                // finish();
                                //Toast.makeText(getApplicationContext(),"Login successfully!!!",Toast.LENGTH_LONG).show();
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

                params.put("client_id",id);
                params.put("client_name",name.getText().toString());
                params.put("client_email",email.getText().toString());
                params.put("client_mobie_no",phone.getText().toString());
                params.put("client_address",address.getText().toString());
                params.put("client_state", state.getText().toString());
                params.put("client_city", city.getText().toString());
                params.put("client_pincode",pincode.getText().toString());






                return params;
            }
        };
        queue.add(client_update);
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
   /* @Override
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
        Intent intent = new Intent(UpdateClient.this, ClientViewDetails.class);
        startActivity(intent);
        finish();
    }*/
}
