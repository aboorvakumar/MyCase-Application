package com.compunet.mycase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class ManageCase extends AppCompatActivity {

    private String URLstring = "http://demo.compunet.in/advocateApi/public/caseDetails";
    private static ProgressDialog mProgressDialog;
    ArrayList<DataModel> dataModelArrayList;
    private RvAdapter rvAdapter;
    private RecyclerView recyclerView;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managecase);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Case");

        EditText search_case = (EditText)findViewById(R.id.search_case);
        search_case.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterManageCase(s.toString());

            }
        });


        recyclerView = findViewById(R.id.recycler);
        fetchingJSON();


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void filterManageCase(String text) {

        ArrayList<DataModel> filterdCase = new ArrayList<>();


        for (DataModel list : dataModelArrayList) {

            if (list.getclient_name().toLowerCase().contains(text.toLowerCase())
                    || list.getcase_name().toLowerCase().contains(text.toLowerCase())
                    || list.getcase_date().toLowerCase().contains(text.toLowerCase())) {

                filterdCase.add(list);
            }
        }

        rvAdapter = new RvAdapter(this,filterdCase);
        recyclerView.setAdapter(rvAdapter);
        //adapter.filterList(filterdNames);
    }

    private void fetchingJSON() {

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        try {

                            removeSimpleProgressDialog();

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("result").equals("success")){

                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("case");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    DataModel playerModel = new DataModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setcase_name(dataobj.getString("case_name"));
                                    playerModel.setclient_name(dataobj.getString("client_name"));
                                    playerModel.setcase_id(dataobj.getString("case_id"));
                                    playerModel.setcase_date(dataobj.getString("case_date"));
                                    playerModel.setcase_time(dataobj.getString("case_time"));
                                    dataModelArrayList.add(playerModel);

                                    editor.putString("case_id",dataobj.getString("case_id"));
                                    String caseid = dataobj.getString("case_id");

                                    editor.apply();
                                    Log.d("caaseid",caseid);

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
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    private void setupRecycler(){

        rvAdapter = new RvAdapter(this,dataModelArrayList);
        recyclerView.setAdapter(rvAdapter);
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
        Intent intent = new Intent(ManageCase.this, LawyerDashboard.class);
        startActivity(intent);
        finish();
    }
}
