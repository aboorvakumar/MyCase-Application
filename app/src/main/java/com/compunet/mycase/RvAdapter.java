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

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataModel> dataModelArrayList;
    private Context mContext;
    private ArrayList<String> card = new ArrayList<>();

    public RvAdapter(Context ctx, ArrayList<DataModel> dataModelArrayList){
        mContext = ctx;

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
       // activity.startActivity(new Intent(activity, NVirementEmmeteur.class));
        /*Intent intent = new Intent(mContext, OtherActivity.class);
        mContext.startActivity(intent);*/
    }



    @Override
    public RvAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_one, parent, false);
        MyViewHolder holder = new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(RvAdapter.MyViewHolder holder, final int position) {


        holder.case_name.setText(dataModelArrayList.get(position).getcase_name());
        holder.client_name.setText(dataModelArrayList.get(position).getclient_name());
        holder.case_id.setText(dataModelArrayList.get(position).getcase_id());
        holder.case_date.setText(dataModelArrayList.get(position).getcase_date());
        holder.case_time.setText(dataModelArrayList.get(position).getcase_time());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),ViewCaseDetails.class);
                intent.putExtra("case_id", dataModelArrayList.get(position).getcase_id());
                view.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout line;
        TextView client_name, case_name, case_id,case_date,case_time;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);


            case_name = (TextView) itemView.findViewById(R.id.casename_txt);
            client_name = (TextView) itemView.findViewById(R.id.clientname_txt);
            case_id = (TextView) itemView.findViewById(R.id.id);

            case_date = (TextView) itemView.findViewById(R.id.casedate);
            case_time = (TextView) itemView.findViewById(R.id.case_time);
        }

    }


}
