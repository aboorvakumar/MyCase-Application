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

public class ClientViewDetailAdapter extends RecyclerView.Adapter<ClientViewDetailAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataModelClientViewDetails> dataModelClientViewDetails ;

    Button editing,delete;

    public ClientViewDetailAdapter(Context ctx, ArrayList<DataModelClientViewDetails> dataModelClientViewDetails){

        inflater = LayoutInflater.from(ctx);
        this.dataModelClientViewDetails = dataModelClientViewDetails;
    }

    @Override
    public ClientViewDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_six, parent, false);
        ClientViewDetailAdapter.MyViewHolder holder = new ClientViewDetailAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ClientViewDetailAdapter.MyViewHolder holder, final int position) {


        holder.client_name.setText(dataModelClientViewDetails.get(position).getclient_name());
        holder.client_email.setText(dataModelClientViewDetails.get(position).getclient_email());

        holder.client_mobile.setText(dataModelClientViewDetails.get(position).getclient_mobile());
        holder.client_address.setText(dataModelClientViewDetails.get(position).getclient_address());

        holder.client_state.setText(dataModelClientViewDetails.get(position).getclient_state());
        holder.client_city.setText(dataModelClientViewDetails.get(position).getclient_city());

        holder.client_pincode.setText(dataModelClientViewDetails.get(position).getclient_pincode());
        holder.client_date.setText(dataModelClientViewDetails.get(position).getclient_date());
        holder.client_id.setText(dataModelClientViewDetails.get(position).getclient_id());
       /* holder.case_date.setText(dataModelCVDS.get(position).getcase_date());
        // holder.time.setText(dataModelCVDS.get(position).gettime());
        holder.case_id.setText(dataModelCVDS.get(position).getcase_id());
       */

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent intent = new Intent(view.getContext(),UpdateClient.class);
               // intent.putExtra("case_id", dataModelCVDS.get(position).getcase_id());
                intent.putExtra("client_id", dataModelClientViewDetails.get(position).getclient_id());

                intent.putExtra("client_name", dataModelClientViewDetails.get(position).getclient_name());
                intent.putExtra("client_email", dataModelClientViewDetails.get(position).getclient_email());

                intent.putExtra("client_mobile", dataModelClientViewDetails.get(position).getclient_mobile());
                intent.putExtra("client_address", dataModelClientViewDetails.get(position).getclient_address());
                intent.putExtra("client_state", dataModelClientViewDetails.get(position).getclient_state());
                intent.putExtra("client_city", dataModelClientViewDetails.get(position).getclient_city());
                intent.putExtra("client_pincode", dataModelClientViewDetails.get(position).getclient_pincode());
                intent.putExtra("client_date", dataModelClientViewDetails.get(position).getclient_date());

                view.getContext().startActivity(intent);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelClientViewDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView client_name,client_email,client_mobile,client_address,client_state,client_city,client_pincode,client_date,client_id;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);


            client_name = (TextView) itemView.findViewById(R.id.name);
            client_email = (TextView) itemView.findViewById(R.id.email);

            client_mobile = (TextView) itemView.findViewById(R.id.phone);
            client_address = (TextView) itemView.findViewById(R.id.address);

            client_state = (TextView) itemView.findViewById(R.id.state);
            client_city = (TextView) itemView.findViewById(R.id.city);


            client_pincode = (TextView) itemView.findViewById(R.id.pin);
            client_date = (TextView) itemView.findViewById(R.id.date);

            client_id = (TextView) itemView.findViewById(R.id.client);
            /*case_date = (TextView) itemView.findViewById(R.id.date);
            //  time = (TextView) itemView.findViewById(R.id.time);
            case_id = (TextView) itemView.findViewById(R.id.case_id);
            */

            editing = (Button)itemView.findViewById(R.id.editing);
            editing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Context context = v.getContext();
                    Intent intent = new Intent(context,UpdateClient.class);
                    // intent.putExtra("case_id", dataModelCVDS.get(position).getcase_id());
                    intent.putExtra("client_id", dataModelClientViewDetails.get(position).getclient_id());

                    intent.putExtra("client_name", dataModelClientViewDetails.get(position).getclient_name());
                    intent.putExtra("client_email", dataModelClientViewDetails.get(position).getclient_email());

                    intent.putExtra("client_mobile", dataModelClientViewDetails.get(position).getclient_mobile());
                    intent.putExtra("client_address", dataModelClientViewDetails.get(position).getclient_address());
                    intent.putExtra("client_state", dataModelClientViewDetails.get(position).getclient_state());
                    intent.putExtra("client_city", dataModelClientViewDetails.get(position).getclient_city());
                    intent.putExtra("client_pincode", dataModelClientViewDetails.get(position).getclient_pincode());
                    intent.putExtra("client_date", dataModelClientViewDetails.get(position).getclient_date());

                    context.startActivity(intent);
                }
            });

            delete = (Button)itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://demo.compunet.in/advocateApi/public/deleteClient";
                    //delVolley();
                    final Context context = v.getContext();
                    RequestQueue requestQueue = (RequestQueue) Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {

                                    //editor.putString("status", String.valueOf(flag));


                                    Log.d("Response",response);

                                    try {
                                        JSONObject obj = new JSONObject(response);




                                        if (obj.getString("result").equals("success") ){
                                            Intent intent = new Intent(context,ManageClient.class);
                                            context.startActivity(intent);

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
                            params.put("client_id", client_id.getText().toString());


                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            });



        }

    }
}
