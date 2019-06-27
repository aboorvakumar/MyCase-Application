package com.compunet.mycase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterClient extends RecyclerView.Adapter<AdapterClient.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataModelNotification> dataModelNotificationsarraylist;
    private Context mContext;
    private ArrayList<String> card = new ArrayList<>();

    public AdapterClient(Context ctx, ArrayList<DataModelNotification> dataModelNotificationsarraylist){
        mContext = ctx;

        inflater = LayoutInflater.from(ctx);
        this.dataModelNotificationsarraylist = dataModelNotificationsarraylist;
        // activity.startActivity(new Intent(activity, NVirementEmmeteur.class));
        /*Intent intent = new Intent(mContext, OtherActivity.class);
        mContext.startActivity(intent);*/
    }



    @Override
    public AdapterClient.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_seven, parent, false);
        MyViewHolder holder = new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterClient.MyViewHolder holder, final int position) {


        holder.subject.setText(dataModelNotificationsarraylist.get(position).getsubject());
        holder.description.setText(dataModelNotificationsarraylist.get(position).getdescription());
        holder.date.setText(dataModelNotificationsarraylist.get(position).getdate());
        holder.time.setText(dataModelNotificationsarraylist.get(position).gettime());
        //holder.client_name.setText(dataModelNotificationsarraylist.get(position).getclient_name());




    }



    @Override
    public int getItemCount() {
        return dataModelNotificationsarraylist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout line;
        TextView subject, description,date,time,client_name;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);


            subject = (TextView) itemView.findViewById(R.id.subject);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            //client_name = (TextView)itemView.findViewById(R.id.client_name);
        }

    }


}