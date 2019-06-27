package com.compunet.mycase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class UpdateCaseDetails extends AppCompatActivity {
    ArrayAdapter<String> spinnerArrayAdapter;
    private final int jsoncode = 1;
    private static ProgressDialog mProgressDialog;
    private ArrayList<ModelData> modelDataArrayList;
    private ArrayList<String> names = new ArrayList<String>();
    JSONObject dataobj;
    private String jsonURL = "http://demo.compunet.in/advocateApi/public/selectClient";


    String CASE_NAME,CLIENT_NAME,CASE_NO,CASE_DETAILS,CASE_TITLE,SPINNER_DROP;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String  case_id,client_id,case_name="",client_name="", case_number="",case_title="",case_details="",case_status="",client_email="",client_mobile="";
    //String id;
    Button btn_update;
    Spinner spinner;
    Spinner spinner1;
    String ite,value;
    private ArrayList<String> id = new ArrayList<String>();

    EditText casename,casenumber,casetitle,casedetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatecasedetails);

        Intent intent = getIntent();
        if (null != intent){
            case_id = intent.getStringExtra("case_id");
            client_id = intent.getStringExtra("client_id");
          //  Log.d("respose",case_id);

            //client_email = intent.getStringExtra("client_email");

            //client_mobile = intent.getStringExtra("client_mobile");

            case_name = intent.getStringExtra("case_name");


            client_name = intent.getStringExtra("client_name");

            case_number = intent.getStringExtra("case_number");

            case_title = intent.getStringExtra("case_title");

            case_details = intent.getStringExtra("case_details");

            case_status = intent.getStringExtra("case_status");


                        //Show the id here
            //Toast.makeText(getApplicationContext(), "id "+client_id , Toast.LENGTH_LONG).show();

            //editor.putString("case_id",id);
        }
        else {

            Toast.makeText(getApplication(),"try Again!!!",Toast.LENGTH_SHORT);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Case Details");


        spinner1 = findViewById(R.id.spCompany);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ite = id.get(i);

                Log.d("ttt",ite);
               // setDefaults("id",ite,getApplicationContext());
                //showing id here
               // Toast.makeText(getApplicationContext(), ite,Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


        casename = (EditText)findViewById(R.id.casename);

        casenumber = (EditText)findViewById(R.id.casenumber);
        casetitle = (EditText)findViewById(R.id.casetitle);
        casedetails = (EditText)findViewById(R.id.casedetails);
        spinner = findViewById(R.id.spinner);
//        spinner.setOnItemSelectedListener(new ItemSelectedListener());
        ArrayList<String> stringArrayList=new ArrayList<>();
        stringArrayList.add("Status");
        stringArrayList.add("OPENED");
        stringArrayList.add("CLOSED");
        stringArrayList.add("PENDING");
        stringArrayList.add("RE-OPENED");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,stringArrayList);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        selectJSON();

        btn_update  = (Button)findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateVolley();
            }
        });
        



        casename.setText(case_name);

        casenumber.setText(case_number);
        casetitle.setText(case_title);
        casedetails.setText(case_details);
        spinner.setSelection(arrayAdapter.getPosition(case_status.toUpperCase()));


    }

    private void selectJSON() {
        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {
                    HttpRequest req = new HttpRequest(jsonURL);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                // Log.d("newwwss",result);
                onTaskCompleted(result,jsoncode);
            }
        }.execute();
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
    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response);
        switch (serviceCode) {
            case jsoncode:

                if (isSuccess(response)) {
                    removeSimpleProgressDialog();  //will remove progress dialog

                    modelDataArrayList = parseInfo(response);
                    // Application of the Array to the Spinner
                    names.clear();
                    for (int i = 0; i < modelDataArrayList.size(); i++){
                        names.add(modelDataArrayList.get(i).getName().toString()) ;
                        id.add(modelDataArrayList.get(i).getid().toString());

                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, names);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinner1.setAdapter(spinnerArrayAdapter);

                    spinner1.setSelection(spinnerArrayAdapter.getPosition(client_name));

                }else {
                    Toast.makeText(UpdateCaseDetails.this, getErrorCode(response), Toast.LENGTH_SHORT).show();
                }
        }
    }
    public ArrayList<ModelData> parseInfo(String response) {
        ArrayList<ModelData> tennisModelArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("result").equals("success")) {

                JSONArray dataArray = jsonObject.getJSONArray("clients");

                for (int i = 0; i < dataArray.length(); i++) {

                    ModelData playersModel = new ModelData();
                    dataobj = dataArray.getJSONObject(i);
                    playersModel.setName(dataobj.getString("name"));
                    playersModel.setid(dataobj.getString("id"));
                    tennisModelArrayList.add(playersModel);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tennisModelArrayList;
    }
    public boolean isSuccess(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("result").equals("success")) {
                return true;
            } else {

                return false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getErrorCode(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString("msg");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
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

    private void updateVolley() {
        String URLupdate = "http://demo.compunet.in/advocateApi/public/updateCase";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest case_update = new StringRequest(Request.Method.POST, URLupdate,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {




                        Log.d("Response",response);

                        try {
                            JSONObject obj = new JSONObject(response);




                            if (obj.getString("result").equals("success") ){
                                Intent upc = new Intent(UpdateCaseDetails.this,ManageCase.class);
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
                params.put("case_id", case_id);
                params.put("client_id",ite);

                params.put("client_name",spinner1.getSelectedItem().toString());
                params.put("status", spinner.getSelectedItem().toString());

                params.put("case_name",casename.getText().toString());
                params.put("case_number", casenumber.getText().toString());
                params.put("case_title", casetitle.getText().toString());
                params.put("case_details",casedetails.getText().toString() );

                //Log.d("rrrrr",case_status);






                return params;
            }
        };
        queue.add(case_update);



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
        Intent intent = new Intent(UpdateCaseDetails.this, ViewCaseDetails.class);
        startActivity(intent);
        finish();
    }*/

}
