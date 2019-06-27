package com.compunet.mycase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class SignIn extends AppCompatActivity  {
    AwesomeValidation awesomeValidation;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    Button viewmanagecase;


    String  laywerurl = "http://demo.compunet.in/advocateApi/public/login";
    EditText emailInput_signIn,passInput_signIn;
    Button signInBtn_signIn,client_screen;
    String  flag = String.valueOf(1);
    TextView forgot_passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        getSupportActionBar().hide();

/*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lawyer Login");*/

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        /*forgot_passwd = (TextView)findViewById(R.id.forgot_passwd);
        forgot_passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forg = new Intent(SignIn.this,ForgotPassword.class);
                startActivity(forg);
            }
        });*/

        emailInput_signIn = (EditText)findViewById(R.id.emailInput_signIn);
        passInput_signIn = (EditText) findViewById(R.id.passInput_signIn);
        signInBtn_signIn = (Button)findViewById(R.id.signInBtn_signIn);
        client_screen = (Button)findViewById(R.id.client_screen);
        client_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent client = new Intent(SignIn.this,ClientSignin.class);
                startActivity(client);
            }
        });


        signInBtn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lawyerVolley();




            }
        });
    }




    private void lawyerVolley() {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest laywer = new StringRequest(Request.Method.POST, laywerurl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        //editor.putString("status", String.valueOf(flag));


                        Log.d("Response",response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            //JSONObject userObj = obj.getJSONObject("user");





                             if (obj.getString("result").equals("success") ){
                                Intent cli = new Intent(SignIn.this,LawyerDashboard.class);
                                startActivity(cli);
                                finish();

                                 editor.putString("username",emailInput_signIn.getText().toString());
                                 editor.putString("password",passInput_signIn.getText().toString());
                                 editor.putString("status",flag);
                                 editor.commit();

                                 //Retrive the value
                                 String restoredText = prefs.getString("username", null);
                                 String retrive = prefs.getString("status", null);
                                 Log.d("retrive",retrive);

                                //Toast.makeText(getApplicationContext(),"Login successfully!!!",Toast.LENGTH_LONG).show();
                            }
                            else {
                                 Toast.makeText(getApplicationContext(),"Email or Password incorrect",Toast.LENGTH_LONG).show();
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
                params.put("username",emailInput_signIn.getText().toString() );
                params.put("password", passInput_signIn.getText().toString());

                params.put("status", flag);

                return params;
            }
        };
        queue.add(laywer);
    }

   /* public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}

/*
public class SignIn extends AppCompatActivity {

    EditText email,password;
    Button signInBtn_signIn,signInBtn, client_screen;
    String status;

    TextView invalidDetails, forgot_password;
    String myJSON;
    ArrayList<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        signInBtn=(Button)findViewById(R.id.signInBtn_signIn);

        email=(EditText) findViewById(R.id.emailInput_signIn);
        password=(EditText)findViewById(R.id.passInput_signIn);
        invalidDetails=(TextView)findViewById(R.id.invalidDetails);
        client_screen = (Button)findViewById(R.id.client_screen);
        client_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent client = new Intent(SignIn.this, ClientSignin.class);
                startActivity(client);
            }
        });
       // forgot_password=(TextView)findViewById(R.id.forgot_password);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SignIn");
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

       */
/* forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toForgotPassword = new Intent(SignIn.this, ForgotPassword.class);
                startActivity(toForgotPassword);
            }
        });*//*


    }

    private void signIn(){
        String emailId=email.getText().toString();
        String pass=password.getText().toString();

        JSONObject logIn = new JSONObject();
        try {
            logIn.put("user_name", emailId);
            logIn.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.putString("flag", status);
        editor.putString("emailId",emailId);
        editor.putString("pass",pass);
        editor.commit();

        if (TextUtils.isEmpty(emailId)){
            email.setError("please fill this field");
            return;
        }
        else
        {
            email.setError(null);
        }
        if (TextUtils.isEmpty(pass)){

            password.setError("please fill this field");
            return;
        }
        else
        {
            password.setError(null);
        }

        nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("logIn",logIn.toString()));
        StrictMode.setThreadPolicy(policy);
        new ClientId().execute("http://advocate.compunet.in/login_client.php");

    }

    private class LogIn extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(SignIn.this, "",
                    "Signing In...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            String responseStr="";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(params[0]);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
            } catch (Exception ex) {
                ex.printStackTrace();
                responseStr="networkerror";
            }
            return responseStr;
        }
        @Override
        protected void onPostExecute(String result) {
            JSONObject reader;
            String output = result.replaceAll("\\s+", "");
            try {
                reader = new JSONObject(output);
                JSONObject res=reader.getJSONObject("result");
                output=res.getString("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(output.equals("networkerror"))
            {
                dialog.dismiss();
                Toast.makeText(SignIn.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            else if(output.equals("invalid_user")) {
                invalidDetails.setVisibility(View.VISIBLE);
                dialog.dismiss();
                // Toast.makeText(SignIn.this, "id"+output, Toast.LENGTH_SHORT).show();
            }else if(output.equals("no_user_found")) {
                invalidDetails.setVisibility(View.VISIBLE);
                dialog.dismiss();
                // Toast.makeText(SignIn.this, "id"+output, Toast.LENGTH_SHORT).show();
            }
            else if(output.equals("success")){
                */
/*Toast.makeText(SignIn.this, "id"+output, Toast.LENGTH_SHORT).show();*//*

                dialog.dismiss();

                myJSON = output.toString();

                Intent toMain = new Intent(SignIn.this, MainActivity.class);
                startActivity(toMain);
                finish();

            }else {
                */
/*Toast.makeText(SignIn.this, output, Toast.LENGTH_SHORT).show();*//*

                dialog.dismiss();

            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class ClientId extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(SignIn.this, "",
                    "Signing In...", true);
        }

        @Override
        protected String doInBackground(String... params) {
            String responseStr="";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(params[0]);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                org.apache.http.HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
            } catch (Exception ex) {
                ex.printStackTrace();
                responseStr="networkerror";
            }
            return responseStr;
        }
        @Override
        protected void onPostExecute(String result) {
            JSONObject reader;
            String output = result.replaceAll("\\s+", "");
            try {
                reader = new JSONObject(output);
                JSONObject res=reader.getJSONObject("result");
                output=res.getString("data");
                if(output.equals("success")){
                    String client_id=res.getString("client_id");

                    editor.putString("client_id",client_id);
                    editor.commit();

                    */
/*Toast.makeText(SignIn.this, "Client ID : "+ prefs.getString("client_id",null), Toast.LENGTH_SHORT).show();*//*

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(output.equals("networkerror"))
            {
                dialog.dismiss();
                Toast.makeText(SignIn.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            else if(output.equals("invalid_user")) {
                invalidDetails.setVisibility(View.VISIBLE);
                dialog.dismiss();
                // Toast.makeText(SignIn.this, "id"+output, Toast.LENGTH_SHORT).show();
            }else if(output.equals("no_user_found")) {
                invalidDetails.setVisibility(View.VISIBLE);
                dialog.dismiss();
                // Toast.makeText(SignIn.this, "id"+output, Toast.LENGTH_SHORT).show();
            }
            else if(output.equals("success")){

                */
/*Toast.makeText(SignIn.this, "Client ID : "+ client_id_toast, Toast.LENGTH_SHORT).show();*//*

                dialog.dismiss();

                myJSON = output.toString();

                Intent toMain = new Intent(SignIn.this, LawyerDashboard.class);
                startActivity(toMain);
                finish();

            }else {
                */
/*Toast.makeText(SignIn.this, output, Toast.LENGTH_SHORT).show();*//*

                dialog.dismiss();

            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }
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

}
*/

