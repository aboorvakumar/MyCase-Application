package com.compunet.mycase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
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

public class ViewNotificationAdvocate extends AppCompatActivity {



    private String URLstring = "http://demo.compunet.in/advocateApi/public/viewNotification";
    private static ProgressDialog mProgressDialog;
    ArrayList<DataModelNotification> dataModelNotificationsarraylist;
    private AdapterAdvocate adapterAdvocate;
    private RecyclerView recyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_notification_advocate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Notification");

        EditText search_notification = (EditText) findViewById(R.id.search_notification);
        search_notification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        recyclerView = findViewById(R.id.recycler);
        advocateVolley();
    }

    private void filter(String text) {
        ArrayList<DataModelNotification> filterdNames = new ArrayList<>();


        for (DataModelNotification item : dataModelNotificationsarraylist) {

            if (item.getclient_name().toLowerCase().contains(text.toLowerCase())
                    || item.getsubject().toLowerCase().contains(text.toLowerCase())
                    || item.getdescription().toLowerCase().contains(text.toLowerCase())
                    || item.getdate().toLowerCase().contains(text.toLowerCase())) {

                filterdNames.add(item);
            }
        }

        adapterAdvocate = new AdapterAdvocate(this,filterdNames);
        recyclerView.setAdapter(adapterAdvocate);
    }

    private void advocateVolley() {



       showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        try {

                            removeSimpleProgressDialog();

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("result").equals("success")){

                                dataModelNotificationsarraylist = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("notifications");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    DataModelNotification dataModelNotification = new DataModelNotification();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    dataModelNotification.setsubject(dataobj.getString("subject"));
                                    dataModelNotification.setdescription(dataobj.getString("description"));
                                    dataModelNotification.setdate(dataobj.getString("date"));
                                    dataModelNotification.settime(dataobj.getString("time"));
                                    dataModelNotification.setclient_name(dataobj.getString("client_name"));

                                    dataModelNotificationsarraylist.add(dataModelNotification);



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

                params.put("user", "advocate");
                params.put("user_id", "111");


                return params;
            }
        };
        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }
    private void setupRecycler(){

        adapterAdvocate = new AdapterAdvocate(this,dataModelNotificationsarraylist);
        recyclerView.setAdapter(adapterAdvocate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }
    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
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
        Intent intent = new Intent(ViewNotificationAdvocate.this, LawyerDashboard.class);
        startActivity(intent);
        finish();
    }
}
