package com.compunet.mycase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ManageClientAdapter extends RecyclerView.Adapter<ManageClientAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataModelManageClient> dataModelManageClients;
    private Context mContext;
    private ArrayList<String> card = new ArrayList<>();

    public ManageClientAdapter(Context ctx, ArrayList<DataModelManageClient> dataModelManageClients){
        mContext = ctx;

        inflater = LayoutInflater.from(ctx);
        this.dataModelManageClients = dataModelManageClients;
        // activity.startActivity(new Intent(activity, NVirementEmmeteur.class));
        /*Intent intent = new Intent(mContext, OtherActivity.class);
        mContext.startActivity(intent);*/
    }



    @Override
    public ManageClientAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_five, parent, false);
        MyViewHolder holder = new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(ManageClientAdapter.MyViewHolder holder, final int position) {


        holder.client_name.setText(dataModelManageClients.get(position).getclient_name());
        holder.client_email.setText(dataModelManageClients.get(position).getclient_email());
        holder.client_mobile.setText(dataModelManageClients.get(position).getclient_mobile());
        holder.client_date.setText(dataModelManageClients.get(position).getclient_date());
        holder.client_id.setText(dataModelManageClients.get(position).getclient_id());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),ClientViewDetails.class);
                intent.putExtra("client_id",dataModelManageClients .get(position).getclient_id());

                view.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return dataModelManageClients.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout line;
        TextView client_name, client_email, client_mobile,client_date,client_time,client_id;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);


            client_name = (TextView) itemView.findViewById(R.id.client_name);
            client_email = (TextView) itemView.findViewById(R.id.client_email);
            client_mobile = (TextView) itemView.findViewById(R.id.client_mobile);

            client_date = (TextView) itemView.findViewById(R.id.client_date);
            client_id = (TextView) itemView.findViewById(R.id.client_id);
        }

    }


}

