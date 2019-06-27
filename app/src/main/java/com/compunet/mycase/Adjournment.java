package com.compunet.mycase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class Adjournment extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    String sub,body_here;
    String j,k;

    public static final String MyPREFERENCES = "MyPrefs" ;
    LinearLayout linear;

    private String client = "http://demo.compunet.in/advocateApi/public/viewNotification";
    private static ProgressDialog mProgressDialog;
    ArrayList<DataModelNotification> dataModelNotificationsarraylist;
    private AdapterAdvocate adapterAdvocate;
    private RecyclerView recyclerView;
    String id;
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.adjournment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Adjournment");



        linear = (LinearLayout) findViewById(R.id.linear);

        EditText search_adjournment = (EditText)findViewById(R.id.search_adjournment);
        search_adjournment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                filterAdjour(s.toString());
            }
        });

         id = getDefaults("id",getApplicationContext());
        recyclerView = findViewById(R.id.recycler);
        clientVolley();
        //method for Remove
        enableSwipeToDeleteAndUndo();

    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final DataModelNotification item = adapterAdvocate.getData().get(position);

                adapterAdvocate.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(linear, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapterAdvocate.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void filterAdjour(String text) {
        //new array list that will hold the filtered data
        ArrayList<DataModelNotification> filteradjour = new ArrayList<>();


        for (DataModelNotification i : dataModelNotificationsarraylist) {

            if (i.getsubject().toLowerCase().contains(text.toLowerCase())
                    || i.getdate().toLowerCase().contains(text.toLowerCase()))
           // || i.getdescription().toLowerCase().contains(text.toLowerCase()))
            {

                filteradjour.add(i);
            }
        }

        adapterAdvocate = new AdapterAdvocate(this,filteradjour);
        recyclerView.setAdapter(adapterAdvocate);
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    private void clientVolley() {

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest client_req = new StringRequest(Request.Method.POST, client,
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
                                    JSONObject data = dataArray.getJSONObject(i);

                                    dataModelNotification.setsubject(data.getString("subject"));
                                    dataModelNotification.setdescription(data.getString("description"));
                                    dataModelNotification.setdate(data.getString("date"));

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

                params.put("user", "client");
                params.put("user_id", id);


                return params;
            }
        };
        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(client_req);
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
        Intent intent = new Intent(Adjournment.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /*public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
