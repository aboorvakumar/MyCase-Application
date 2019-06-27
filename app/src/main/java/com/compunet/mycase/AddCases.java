package com.compunet.mycase;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCases extends AppCompatActivity implements View.OnClickListener {

    private String jsonURL = "http://demo.compunet.in/advocateApi/public/selectClient";

    private final int jsoncode = 1;
    private static ProgressDialog mProgressDialog;
    private ArrayList<ModelData> modelDataArrayList;
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<String>();
    private Spinner spinner;
    JSONObject dataobj;
     String ite;


    private Spinner spinner1;
    String datastoreid,datastore_name;
    String caseurl = "http://demo.compunet.in/advocateApi/public/insertCase";
    private Button save;
    private EditText txt_caseno,txt_casetitle,txt_casename,txt_cdetails;

    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcases);


        spinner = findViewById(R.id.spCompany);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                 ite = id.get(i);
                 Log.d("ttt",ite);
                setDefaults("id",ite,getApplicationContext());
                //showing the id here
               // Toast.makeText(getApplicationContext(), ite,Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });



        loadJSON();


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Case");



        txt_caseno = (EditText)findViewById(R.id.txt_caseno);
        txt_casetitle = (EditText)findViewById(R.id.txt_casetitle);
        //txt_cname = (EditText)findViewById(R.id.txt_cname);

        txt_casename = (EditText)findViewById(R.id.txt_casename);
        txt_cdetails = (EditText)findViewById(R.id.txt_cdetails);

        save = (Button)findViewById(R.id.save);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new ItemSelectedListener());

        //awesomeValidation.addValidation(this, R.id.txt_caseno, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.case_no);

        awesomeValidation.addValidation(this, R.id.txt_casetitle, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.case_title);
        //awesomeValidation.addValidation(this, R.id.txt_cname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.client_name);
        awesomeValidation.addValidation(this, R.id.txt_casename, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.case_name);
        awesomeValidation.addValidation(this, R.id.txt_cdetails, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.case_details);
       // awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.nameerror);
        //awesomeValidation.addValidation(this, R.id.txt_caseno, "^[2-9]{2}[0-9]{8}$", R.string.case_no);
        //awesomeValidation.addValidation(this, R.id.editTextDob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.nameerror);
        //awesomeValidation.addValidation(this, R.id.editTextAge, Range.closed(13, 60), R.string.ageerror);

        save.setOnClickListener(this);

    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadJSON(){

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

    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response);
        switch (serviceCode) {
            case jsoncode:

                if (isSuccess(response)) {
                    removeSimpleProgressDialog();  //will remove progress dialog

                    modelDataArrayList = parseInfo(response);
                    // Application of the Array to the Spinner

                    for (int i = 0; i < modelDataArrayList.size(); i++){
                        names.add(modelDataArrayList.get(i).getName().toString()) ;
                        id.add(modelDataArrayList.get(i).getid().toString());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, names);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinner.setAdapter(spinnerArrayAdapter);

                }else {
                    Toast.makeText(AddCases.this, getErrorCode(response), Toast.LENGTH_SHORT).show();
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

                   // editor.putString("name",dataobj.getString("name"));
                   // editor.putString("id",dataobj.getString("id"));
                    datastore_name = dataobj.getString("name");
                    datastoreid = dataobj.getString("id");
                    Log.d("testid",datastoreid);


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

    private void submitForm() {

        if (awesomeValidation.validate()) {
            
            addcaseVolley();
            //Intent mc = new Intent(AddCases.this,ManageCase.class);
            //startActivity(mc);
           // Toast.makeText(this, "Successfully added Cases", Toast.LENGTH_LONG).show();


        }
    }

    private void addcaseVolley() {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest addcasses = new StringRequest(Request.Method.POST, caseurl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response",response);

                        try {
                            JSONObject objaddcase = new JSONObject(response);
                           /* JSONObject userObj = objaddcase.getJSONObject("user");
                            editor.putString("id",userObj.getString("id"));
                            editor.putString("case_name",userObj.getString("case_name"));


                            String caseid = userObj.getString("id");
                            Log.d("comeon" ,caseid);
                            editor.commit();*/




                            if (objaddcase.getString("result").equals("success") ){
                                Intent cli = new Intent(AddCases.this,ManageCase.class);
                                startActivity(cli);
                                finish();
                                //Toast.makeText(getApplicationContext(),"successfully Added",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Try Again!!!",Toast.LENGTH_LONG).show();
                            }


                            /*Retive client id*/
//                           JSONObject objaddcase = new JSONObject(response);
                           /* editor.putString("id",objaddcase.getString("id"));
                            editor.putString("case_name",objaddcase.getString("case_name"));
                            String caseid = objaddcase.getString("id");
                            Log.d("comeon" ,caseid);
                            editor.apply();*/


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

                params.put("case_name",txt_casename.getText().toString() );
                params.put("case_number", txt_caseno.getText().toString());
                params.put("case_title", txt_casetitle.getText().toString());
                params.put("client_id", ite);
                //params.put("client_name",datastore_name);
                params.put("case_details", txt_cdetails.getText().toString());
                params.put("status", spinner1.getSelectedItem().toString());

               // params.put("case_file", flag);


                return params;
            }
        };
        queue.add(addcasses);
    }

    @Override
    public void onClick(View v) {
        if (v == save) {
            submitForm();
        }
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {


        String firstItem = String.valueOf(spinner1.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinner1.getSelectedItem()))) {
                // ToDo when first item is selected
            } else {
                /*Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();*/

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

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
        Intent intent = new Intent(AddCases.this, LawyerDashboard.class);
        startActivity(intent);
        finish();
    }
}
