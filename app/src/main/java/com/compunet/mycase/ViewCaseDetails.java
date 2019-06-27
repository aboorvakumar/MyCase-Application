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

public class ViewCaseDetails extends AppCompatActivity {
    String  case_id= "";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    private String caseviewdetails = "http://demo.compunet.in/advocateApi/public/caseViewDetails";
    private static ProgressDialog mProgressDialog;
    ArrayList<DataModelCVD> dataModelCVDS;
    private VCDAdapter vcdAdapter;
    private RecyclerView recyclerView;
    String id,c_no,c_title,case_name,client_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_case_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Case Details");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();



        Intent intent = getIntent();
        if (null != intent){
            case_id = intent.getStringExtra("case_id");
            //showing id here
            //Toast.makeText(getApplicationContext(), "id "+case_id , Toast.LENGTH_LONG).show();

            editor.putString("case_id",id);
        }
        else {

            Toast.makeText(getApplication(),"try Again!!!",Toast.LENGTH_SHORT);
        }


        recyclerView = findViewById(R.id.recycler);
        fetchingJSON();

    }

    private void fetchingJSON() {

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest Requestcase = new StringRequest(Request.Method.POST, caseviewdetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        try {

                            removeSimpleProgressDialog();

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("result").equals("success")){

                                dataModelCVDS = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("case");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    DataModelCVD cvd = new DataModelCVD();
                                    JSONObject dataobject = dataArray.getJSONObject(i);

                                    cvd.setcase_number(dataobject.getString("case_number"));
                                    cvd.setcase_title(dataobject.getString("case_title"));
                                    cvd.setcase_name(dataobject.getString("case_name"));
                                    cvd.setcase_details(dataobject.getString("case_details"));
                                    cvd.setclient_name(dataobject.getString("client_name"));
                                    cvd.setcase_status(dataobject.getString("case_status"));
                                    cvd.setclient_email(dataobject.getString("client_email"));
                                    cvd.setclient_mobile(dataobject.getString("client_mobile"));
                                    cvd.setcase_date(dataobject.getString("case_date"));
                                    //cvd.settime(dataobject.getString("time"));
                                    cvd.setcase_id(dataobject.getString("case_id"));
                                    cvd.setclient_id(dataobject.getString("client_id"));

                                    dataModelCVDS.add(cvd);

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

                params.put("case_id", case_id);
//                params.put("case_number", c_no);
//                params.put("case_title", c_title);
//                params.put("case_name", case_name);
//                params.put("client_name", client_name);
//

                return params;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(Requestcase);


    }

    private void setupRecycler(){

        vcdAdapter = new VCDAdapter(this,dataModelCVDS);
        recyclerView.setAdapter(vcdAdapter);
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
        Intent intent = new Intent(ViewCaseDetails.this, ManageCase.class);
        startActivity(intent);
        finish();
    }


}
