package com.compunet.mycase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientViewDetails extends AppCompatActivity {

    String  client_id= "";


    private String url = "http://demo.compunet.in/advocateApi/public/clientViewDetails";
    private static ProgressDialog mProgressDialog;
    ArrayList<DataModelClientViewDetails> dataModelClientViewDetails;
    private ClientViewDetailAdapter clientViewDetailAdapter;
    private RecyclerView recyclerView;
    String id,c_no,c_title,case_name,client_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_client_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Client Details");



        Intent intent = getIntent();
        if (null != intent){
            client_id = intent.getStringExtra("client_id");
            //show the id here
            //Toast.makeText(getApplicationContext(), "id "+client_id , Toast.LENGTH_LONG).show();


        }
        else {

            Toast.makeText(getApplication(),"try Again!!!",Toast.LENGTH_SHORT);
        }


        recyclerView = findViewById(R.id.recycler);
        fetchingJSON();

    }

    private void fetchingJSON() {

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest client = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        try {

                            removeSimpleProgressDialog();

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("result").equals("success")){

                                dataModelClientViewDetails= new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("client");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    DataModelClientViewDetails  model = new DataModelClientViewDetails();
                                    JSONObject dataobject = dataArray.getJSONObject(i);

                                    model.setclient_name(dataobject.getString("client_name"));
                                    model.setclient_email(dataobject.getString("client_email"));
                                    model.setclient_mobile(dataobject.getString("client_mobile"));
                                    model.setclient_address(dataobject.getString("client_address"));
                                    model.setclient_state(dataobject.getString("client_state"));
                                    model.setclient_city(dataobject.getString("client_city"));
                                    model.setclient_pincode(dataobject.getString("client_pincode"));
                                    model.setclient_date(dataobject.getString("client_date"));
                                    model.setclient_id(dataobject.getString("client_id"));
                                   /* cvd.setcase_date(dataobject.getString("case_date"));
                                    //cvd.settime(dataobject.getString("time"));
                                    cvd.setcase_id(dataobject.getString("case_id"));
                                    */

                                    dataModelClientViewDetails.add(model);

                                }

                                setupRecycler();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Log.d("Error.Response", "Error :" + error.toString());
                        Toast.makeText(getApplicationContext(),"Try Again!!!", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("client_id", client_id);



                return params;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(client);


    }

    private void setupRecycler(){

        clientViewDetailAdapter = new ClientViewDetailAdapter(this,dataModelClientViewDetails);
        recyclerView.setAdapter(clientViewDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    //mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {

            ie.printStackTrace();


        } catch (RuntimeException re) {
            re.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
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
        Intent intent = new Intent(ClientViewDetails.this, ManageClient.class);
        startActivity(intent);
        finish();
    }
}
