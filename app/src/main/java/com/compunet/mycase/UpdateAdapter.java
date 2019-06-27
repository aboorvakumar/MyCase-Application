package com.compunet.mycase;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataModelCVD> dataModelCVDS ;

    public UpdateAdapter(Context ctx, ArrayList<DataModelCVD> dataModelCVDS){

        inflater = LayoutInflater.from(ctx);
        this.dataModelCVDS = dataModelCVDS;
    }

    @Override
    public UpdateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_three, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(UpdateAdapter.MyViewHolder holder, final int position) {


        holder.case_name.setText(dataModelCVDS.get(position).getcase_name());
        holder.client_name.setText(dataModelCVDS.get(position).getclient_name());

        holder.case_number.setText(dataModelCVDS.get(position).getcase_number());
        holder.case_title.setText(dataModelCVDS.get(position).getcase_title());

        holder.case_details.setText(dataModelCVDS.get(position).getcase_details());
        // holder.case_status.setText(dataModelCVDS.get(position).getcase_status());

        holder.client_email.setText(dataModelCVDS.get(position).getclient_email());
        holder.client_mobile.setText(dataModelCVDS.get(position).getclient_mobile());
        holder.case_date.setText(dataModelCVDS.get(position).getcase_date());
        holder.case_id.setText(dataModelCVDS.get(position).getcase_id());
        holder.client_id.setText(dataModelCVDS.get(position).getclient_id());

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),UpdateCaseDetails.class);
                intent.putExtra("case_id", dataModelCVDS.get(position).getcase_id());
                intent.putExtra("client_id", dataModelCVDS.get(position).getclient_id());
                view.getContext().startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return dataModelCVDS.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        EditText client_name, case_name, case_number,case_title,client_mobile,client_email,case_details,case_date,case_id,client_id;
        ImageView iv;
        Spinner case_status;
        public MyViewHolder(View itemView) {
            super(itemView);


            case_name = (EditText) itemView.findViewById(R.id.txt_case_name);
            client_name = (EditText) itemView.findViewById(R.id.txt_client_name);

            case_number = (EditText) itemView.findViewById(R.id.case_no_txt);
            case_title = (EditText) itemView.findViewById(R.id.case_title_txt);

            case_details = (EditText) itemView.findViewById(R.id.case_detail_txt);
            case_status = (Spinner) itemView.findViewById(R.id.status);


            client_email = (EditText) itemView.findViewById(R.id.txt_email);
            client_mobile = (EditText) itemView.findViewById(R.id.txt_mobile_no);
            //case_date = (TextView) itemView.findViewById(R.id.date);
           // case_id = (TextView) itemView.findViewById(R.id.case_id);
            //client_id = (TextView) itemView.findViewById(R.id.client_idtxt);
        }

    }
}

