package com.compunet.mycase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import static com.compunet.mycase.AddCases.setDefaults;

public class Send extends AppCompatActivity implements View.OnClickListener {
    private final int jsoncode = 1;
    private ArrayList<ModelData> modelDataArrayList;
    private static ProgressDialog mProgressDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
   // public static final String SUB = "subjectKey";
   // public static final String BODY = "bodyKey";
   private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<String>();
    private String jsonURL = "http://demo.compunet.in/advocateApi/public/selectClient";
    SharedPreferences sharedpreferences;
    EditText ed1,ed2,ed3;
    JSONObject dataobj;
    private AwesomeValidation awesomeValidation;
    String value;
    String datastoreid,datastore_name;
    private Spinner spinner;
    String ite;
    Button b1;
    String subject,body;
//    SharedPreferences prefs;
//    SharedPreferences.Editor editor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //getSupportActionBar().hide();
//        prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        editor = prefs.edit();

        spinner = findViewById(R.id.spCompany);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ite = id.get(i);

                Log.d("ttt",ite);



                setDefaults("id",ite,getApplicationContext());
                //SHOWING THE ID HERE
                //Toast.makeText(getApplicationContext(), ite,Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        clientJSON();
        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);
        ed3=(EditText)findViewById(R.id.editText3);
         b1=(Button)findViewById(R.id.button);

        awesomeValidation.addValidation(this, R.id.editText2, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.subject);
        awesomeValidation.addValidation(this, R.id.editText3, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.body);
         value = getDefaults("id",getApplicationContext());
       // Log.d("sharedPref:", value);
        b1.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE );


   }

    private void clientJSON() {

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
                    Toast.makeText(Send.this, getErrorCode(response), Toast.LENGTH_SHORT).show();
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

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    @Override
    public void onClick(View v) {
        if (v == b1) {
            submitForm();
        }
    }

    private void submitForm() {

        if (awesomeValidation.validate()) {
            String tittle=ed1.getText().toString();
            subject=ed2.getText().toString();
            ed2.setText("");
            body=ed3.getText().toString();
            ed3.setText("");

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("SUB", subject);
            editor.putString("BODYHERE", body);
            Log.d("SUB",subject);

            editor.commit();

            NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify=new Notification.Builder(getApplicationContext()).setContentText(body).
                    setContentTitle(subject).setSmallIcon(R.drawable.ic_alarm_settings).build();

            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notif.notify(0, notify);
            sendVolley();
            //Toast.makeText(this,subject, Toast.LENGTH_LONG).show();

        }
    }

    private void sendVolley() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://demo.compunet.in/advocateApi/public/sendPushNotification";
        StringRequest single = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


                        Log.d("Response",response);

                        try {
                            JSONObject object = new JSONObject(response);




                            if (object.getString("result").equals("1") ){
                                Intent sss = new Intent(Send.this,LawyerDashboard.class);
                                startActivity(sss);
                                //finish();
                                Toast.makeText(getApplicationContext()," successfully sended",Toast.LENGTH_LONG).show();
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
                params.put("user",ite);
                params.put("subject",subject);
                params.put("description",body);



                return params;
            }
        };
        queue.add(single);


    }




}