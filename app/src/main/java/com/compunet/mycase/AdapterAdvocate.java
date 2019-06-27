package com.compunet.mycase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterAdvocate extends RecyclerView.Adapter<AdapterAdvocate.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataModelNotification> dataModelNotificationsarraylist;
    private Context mContext;
    private ArrayList<String> card = new ArrayList<>();

    public AdapterAdvocate(Context ctx, ArrayList<DataModelNotification> dataModelNotificationsarraylist){
        mContext = ctx;

        inflater = LayoutInflater.from(ctx);
        this.dataModelNotificationsarraylist = dataModelNotificationsarraylist;
        // activity.startActivity(new Intent(activity, NVirementEmmeteur.class));
        /*Intent intent = new Intent(mContext, OtherActivity.class);
        mContext.startActivity(intent);*/
    }



    @Override
    public AdapterAdvocate.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_eight, parent, false);
        MyViewHolder holder = new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterAdvocate.MyViewHolder holder, final int position) {


        holder.subject.setText(dataModelNotificationsarraylist.get(position).getsubject());
        holder.description.setText(dataModelNotificationsarraylist.get(position).getdescription());
        holder.date.setText(dataModelNotificationsarraylist.get(position).getdate());
       // holder.time.setText(dataModelNotificationsarraylist.get(position).gettime());
       // holder.client_name.setText(dataModelNotificationsarraylist.get(position).getclient_name());



       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),ViewCaseDetails.class);
                intent.putExtra("case_id", dataModelArrayList.get(position).getcase_id());
                view.getContext().startActivity(intent);
            }
        });*/

    }



    @Override
    public int getItemCount() {
        return dataModelNotificationsarraylist.size();
    }

    public void removeItem(int position) {
        dataModelNotificationsarraylist.remove(position);
        notifyItemRemoved(position);
    }

    public ArrayList<DataModelNotification> getData() {
        return dataModelNotificationsarraylist;
    }

    public void restoreItem(DataModelNotification item, int position) {
        dataModelNotificationsarraylist.add(position, item);
        notifyItemInserted(position);

    }

    /*public void restoreItem(String item, int position) {
        dataModelNotificationsarraylist.add(position, item);
        notifyItemInserted(position);
    }*/

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout line;
        TextView subject, description,date,time,client_name;
        ImageView iv;
        Button report;

        public MyViewHolder(View itemView) {
            super(itemView);


            subject = (TextView) itemView.findViewById(R.id.subject);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView)itemView.findViewById(R.id.date);
            //time = (TextView)itemView.findViewById(R.id.time);
            //client_name = (TextView)itemView.findViewById(R.id.client_name);

            //report = (Button)itemView.findViewById(R.id.report);
           /* report.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {

                    createPdf(subject.getText().toString());
                }
            });*/
        }

       /* @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void createPdf(String toString) {
            // create a new document
            PdfDocument document = new PdfDocument();
            // crate a page description
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
            // start a page
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawCircle(50, 50, 30, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(toString, 80, 50, paint);

            document.finishPage(page);

            pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
            page = document.startPage(pageInfo);
            canvas = page.getCanvas();
            paint = new Paint();
            paint.setColor(Color.BLUE);
            canvas.drawCircle(100, 100, 100, paint);
            document.finishPage(page);

            String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Download/";
            File file = new File(directory_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String targetPdf = directory_path+"java.pdf";
            File filePath = new File(targetPdf);
            try {
                document.writeTo(new FileOutputStream(filePath));
                Toast.makeText(mContext, "Done", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.e("main", "error "+e.toString());
                Toast.makeText(mContext, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
            }

            document.close();
        }*/
        }

    }


