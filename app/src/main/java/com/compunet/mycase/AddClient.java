package com.compunet.mycase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddClient extends AppCompatActivity implements View.OnClickListener {
    
    EditText txt_client_name,txt_email,txt_mobileno,txt_address,state,city,pincode;
    Button btn_add,viewmanagecase ;

    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclient);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Client");

        txt_client_name = (EditText)findViewById(R.id.txt_client_name);
        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_mobileno = (EditText)findViewById(R.id.txt_mobileno);
        txt_address = (EditText)findViewById(R.id.txt_address);
        state = (EditText)findViewById(R.id.state);
        city = (EditText)findViewById(R.id.city);
        pincode = (EditText)findViewById(R.id.pincode);

        btn_add = (Button)findViewById(R.id.btn_add);

        awesomeValidation.addValidation(this, R.id.txt_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        btn_add.setOnClickListener(this);
       /* btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVolley();
            }
        });*/


    }

    @Override
    public void onClick(View v) {
        if (v == btn_add) {
            submitForm();
        }
    }

    private void submitForm() {

        if (awesomeValidation.validate()) {

            addVolley();
            //Intent mc = new Intent(AddCases.this,ManageCase.class);
            //startActivity(mc);
            // Toast.makeText(this, "Successfully added Cases", Toast.LENGTH_LONG).show();


        }
    }

    private void addVolley() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String addclient = "http://demo.compunet.in/advocateApi/public/addClient";
        StringRequest addcli = new StringRequest(Request.Method.POST, addclient,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String aboorva) {


                        Log.d("Response",aboorva);

                        try {
                            JSONObject objaddclient = new JSONObject(aboorva);
                           /* JSONObject userObj = obj.getJSONObject("user");
                            editor.putString("id",userObj.getString("id"));
                            editor.putString("email_id",userObj.getString("email_id"));
                            editor.putString("mobile_no",userObj.getString("mobile_no"));
                            editor.commit();*/



                            if (objaddclient.getString("result").equals("success") ){
                                Intent cli = new Intent(AddClient.this, com.compunet.mycase.LawyerDashboard.class);
                                 startActivity(cli);
                                 finish();
                                //Toast.makeText(getApplicationContext()," successfully Added",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();
                            }





                        } catch (Throwable t) {
                            Log.d("My App", "Could not parse malformed JSON: \"" + aboorva + "\"");

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
        queue.add(addcli);
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
        Intent intent = new Intent(AddClient.this, LawyerDashboard.class);
        startActivity(intent);
        finish();
    }


}
