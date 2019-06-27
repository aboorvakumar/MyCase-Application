package com.compunet.mycase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VCDAdapter extends RecyclerView.Adapter<VCDAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataModelCVD> dataModelCVDS ;
    String id_case_id;

    Button btn_upddaate,delete;

    public VCDAdapter(Context ctx, ArrayList<DataModelCVD> dataModelCVDS){

        inflater = LayoutInflater.from(ctx);
        this.dataModelCVDS = dataModelCVDS;
    }

    @Override
    public VCDAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_two, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(VCDAdapter.MyViewHolder holder, final int position) {


        holder.case_name.setText(dataModelCVDS.get(position).getcase_name());
        holder.client_name.setText(dataModelCVDS.get(position).getclient_name());

        holder.case_number.setText(dataModelCVDS.get(position).getcase_number());
        holder.case_title.setText(dataModelCVDS.get(position).getcase_title());

        holder.case_details.setText(dataModelCVDS.get(position).getcase_details());
        holder.case_status.setText(dataModelCVDS.get(position).getcase_status());

        holder.client_email.setText(dataModelCVDS.get(position).getclient_email());
        holder.client_mobile.setText(dataModelCVDS.get(position).getclient_mobile());
        holder.case_date.setText(dataModelCVDS.get(position).getcase_date());
       // holder.time.setText(dataModelCVDS.get(position).gettime());
        holder.case_id.setText(dataModelCVDS.get(position).getcase_id());
        holder.client_id.setText(dataModelCVDS.get(position).getclient_id());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(view.getContext(),UpdateCaseDetails.class);
                intent.putExtra("case_id", dataModelCVDS.get(position).getcase_id());
                intent.putExtra("client_id", dataModelCVDS.get(position).getclient_id());

                intent.putExtra("client_email", dataModelCVDS.get(position).getclient_email());
                intent.putExtra("client_mobile", dataModelCVDS.get(position).getclient_mobile());

                intent.putExtra("case_name", dataModelCVDS.get(position).getcase_name());
                intent.putExtra("client_name", dataModelCVDS.get(position).getclient_name());
                intent.putExtra("case_number", dataModelCVDS.get(position).getcase_number());
                intent.putExtra("case_title", dataModelCVDS.get(position).getcase_title());
                intent.putExtra("case_details", dataModelCVDS.get(position).getcase_details());
                intent.putExtra("case_status", dataModelCVDS.get(position).getcase_status());
                view.getContext().startActivity(intent);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelCVDS.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView client_name,time, case_name, case_number,case_title,client_mobile,client_email,case_status,case_details,case_date,case_id,client_id;
        ImageView iv;
        Button editing;

        public MyViewHolder(final View itemView) {
            super(itemView);


            case_name = (TextView) itemView.findViewById(R.id.casename_txt);
            client_name = (TextView) itemView.findViewById(R.id.clientname_txt);

            case_number = (TextView) itemView.findViewById(R.id.caseno_txt);
            case_title = (TextView) itemView.findViewById(R.id.casetitle_txt);

            case_details = (TextView) itemView.findViewById(R.id.casedetails_txt);
            case_status = (TextView) itemView.findViewById(R.id.status_txt);


            client_email = (TextView) itemView.findViewById(R.id.email);
            client_mobile = (TextView) itemView.findViewById(R.id.phone_txt);
            case_date = (TextView) itemView.findViewById(R.id.date);
          //  time = (TextView) itemView.findViewById(R.id.time);
            case_id = (TextView) itemView.findViewById(R.id.case_id);
            client_id = (TextView) itemView.findViewById(R.id.client_idtxt);

            id_case_id = case_id.getText().toString();
            btn_upddaate = (Button) itemView.findViewById(R.id.editing);
            btn_upddaate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent upd = new Intent(itemView.getContext(),UpdateCaseDetails.class);
                    itemView.getContext().startActivity(upd);*/

                    int position = getAdapterPosition();

                    Context context = v.getContext();

                    Intent intent = new Intent(context,UpdateCaseDetails.class);
                    intent.putExtra("case_id", dataModelCVDS.get(position).getcase_id());
                    intent.putExtra("client_id", dataModelCVDS.get(position).getclient_id());

                    intent.putExtra("client_email", dataModelCVDS.get(position).getclient_email());
                    intent.putExtra("client_mobile", dataModelCVDS.get(position).getclient_mobile());

                    intent.putExtra("case_name", dataModelCVDS.get(position).getcase_name());
                    intent.putExtra("client_name", dataModelCVDS.get(position).getclient_name());
                    intent.putExtra("case_number", dataModelCVDS.get(position).getcase_number());
                    intent.putExtra("case_title", dataModelCVDS.get(position).getcase_title());
                    intent.putExtra("case_details", dataModelCVDS.get(position).getcase_details());
                    intent.putExtra("case_status", dataModelCVDS.get(position).getcase_status());
                    context.startActivity(intent);
                }
            });

            delete = (Button)itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String url = "http://demo.compunet.in/advocateApi/public/deleteCase";
                    //delVolley();
                    final Context context = v.getContext();
                    RequestQueue queue = (RequestQueue) Volley.newRequestQueue(context);
                    StringRequest delete = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {

                                    //editor.putString("status", String.valueOf(flag));


                                    Log.d("Response",response);

                                    try {
                                        JSONObject obj = new JSONObject(response);




                                        if (obj.getString("result").equals("success") ){
                                            Intent dobj = new Intent(context,ManageCase.class);
                                            context.startActivity(dobj);

                                        }
                                        else {
                                            Toast.makeText(context,"Try Again!!!",Toast.LENGTH_LONG).show();
                                        }


                                        //JSONObject userObj = obj.getJSONObject("user");


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
                            params.put("case_id", case_id.getText().toString());


                            return params;
                        }
                    };
                    queue.add(delete);
                }
            });

        }

    }

    /*private void delVolley() {


    }*/
}

