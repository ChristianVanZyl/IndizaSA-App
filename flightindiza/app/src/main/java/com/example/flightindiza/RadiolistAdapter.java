package com.example.flightindiza;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RadiolistAdapter extends RecyclerView.Adapter<RadiolistAdapter.Viewholder> {

    Context context;
    ArrayList<RadioModel> radioModelArrayList;
    List<String> cancelListData = new ArrayList<>();
    String cancelFlightID;
    String cancelFlightUserID = Integer.toString(UserDataHandler.getCurrentUser().getId());


    public RadiolistAdapter() {
    }

    public RadiolistAdapter(Context context, ArrayList<RadioModel> radioModelArrayList) {
        this.context = context;
        this.radioModelArrayList = radioModelArrayList;

    }

    @NonNull
    @Override
    public RadiolistAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radiov, parent, false);
        return new Viewholder(view);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<RadioModel> getRadioModelArrayList() {
        return radioModelArrayList;
    }

    public void setRadioModelArrayList(ArrayList<RadioModel> radioModelArrayList) {
        this.radioModelArrayList = radioModelArrayList;
    }

    @Override
    public void onBindViewHolder(RadiolistAdapter.Viewholder holder, int position) {
        RadioModel model = radioModelArrayList.get(position);
        holder.fliID.setText(model.getFlightID());
        holder.resID.setText(model.getReservationID());
        holder.desID.setText(model.getDestination());
        holder.dat.setText(model.getDateOf());

        cancelFlightID = model.getFlightID();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "I start here");


                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setMessage("Are you sure you want to cancel your flight?")
                            .setCancelable(true)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    ExecutorService service = Executors.newFixedThreadPool(1);
                                    Future<String> waiting = service.submit(new Task("CancelBooking"));
                                    String s = "";
                                    try {
                                        s = waiting.get();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("check", "" + s);
                                    dialog.dismiss();
                                    radioCardButtonChoice();

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    dialog.dismiss();


                            }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.blue));
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.blue));


        }});
    }

    @Override
    public int getItemCount() {
        return radioModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public View view;
        TextView fliID;
        TextView resID;
        TextView desID;
        TextView dat;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            fliID = itemView.findViewById(R.id.flID);
            resID = itemView.findViewById(R.id.resID);
            desID = itemView.findViewById(R.id.fliDes);
            dat = itemView.findViewById(R.id.fliDate);

            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked
                }
            });

        }


    }
    public void radioCardButtonChoice() {

        cancelListData.add(cancelFlightID);
        Log.d("its", "" + cancelListData.get(0));
        cancelListData.add(cancelFlightUserID);
        ExecutorService service = Executors.newFixedThreadPool(1);
        // future(promise) waiting on receiving confirmation from task that data was sent
        Future<List<String>> waiting2 =  service.submit(new TaskCancel(cancelListData));
        // variable getting
        List<String> thisList = new ArrayList<>();
        try {
            thisList = waiting2.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("check", "executor done" );
        ((Activity)context).finish();

    }



}