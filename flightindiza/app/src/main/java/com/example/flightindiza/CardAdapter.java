package com.example.flightindiza;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.Viewholder> {

     Context context;
     ArrayList<CardModel> cardModelArrayList;

    public CardAdapter() {
    }

    public CardAdapter(Context context, ArrayList<CardModel> cardModelArrayList) {
        this.context = context;
        this.cardModelArrayList = cardModelArrayList;
    }

    @NonNull
    @Override
    public CardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardv, parent, false);
        return new Viewholder(view);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<CardModel> getCardModelArrayList() {
        return cardModelArrayList;
    }

    public void setCardModelArrayList(ArrayList<CardModel> cardModelArrayList) {
        this.cardModelArrayList = cardModelArrayList;
    }

    @Override
    public void onBindViewHolder(CardAdapter.Viewholder holder, int position) {
        CardModel model = cardModelArrayList.get(position);
        holder.airport.setText(model.getAirport());
        holder.dateOf.setText(model.getDateOf());
        holder.deptTime.setText(model.getDeptTime());
        holder.duration.setText(model.getDuration());
        holder.destination.setText(model.getDestination());
        holder.arrivalTime.setText(model.getArrivalTime());
        holder.price.setText("R" + model.getPrice());
        holder.seatsLeft.setText(model.getSeatsLeft());
        holder.seatsLeft.setText(model.getSeatsLeft());
        holder.arrowImg.setImageResource(model.getArrowImg());
        holder.theflightID.setText("Flight ID: " + model.getAirportID());

        String num = holder.seatsLeft.getText() + "";
        int amount = Integer.valueOf(num);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ensure that on click if flight is fully booked that user cannot book a ticket
                if(amount != 0 ){
                    Intent i=new Intent(context, activity_book.class);
                    // add to Intent as bundle, data of clicked card passed to next activity
                    i.putExtra("airportID", model.getAirportID());
                    i.putExtra("airport",model.getAirport());
                    i.putExtra("dateOf", model.getDateOf());
                    i.putExtra("deptTime",model.getDeptTime());
                    i.putExtra("duration", model.getDuration());
                    i.putExtra("destination", model.getDestination());
                    i.putExtra("arrivalTime", model.getArrivalTime());
                    i.putExtra("price", model.getPrice());
                    i.putExtra("seats", model.getSeatsLeft());
                    i.putExtra("fID", model.getAirportID());

                    ExecutorService service = Executors.newFixedThreadPool(1);
                    // future(promise) waiting on receiving confirmation from task that data was sent
                    Future<String> waiting =  service.submit(new Task("Book"));
                    // variable getting
                    String s = "";
                    try {
                        s = waiting.get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("check", "" + s);
                    // switching activity
                    context.startActivity(i);
                }else{
                    Toast.makeText(context, "Flight fully booked!", Toast.LENGTH_SHORT)
                            .show();

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return cardModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

         public View view;
         TextView airport;
         TextView deptTime;
         TextView dateOf;
         TextView duration;
         TextView destination;
         TextView arrivalTime;
         TextView price;
         TextView seatsLeft;
         TextView theflightID;
         ImageView arrowImg;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            airport = itemView.findViewById(R.id.airport);
            deptTime = itemView.findViewById(R.id.deptTime);
            dateOf = itemView.findViewById(R.id.dateOf);
            duration = itemView.findViewById(R.id.duration);
            destination = itemView.findViewById(R.id.destination);
            arrivalTime = itemView.findViewById(R.id.arrivalTime);
            price = itemView.findViewById(R.id.price);
            seatsLeft = itemView.findViewById(R.id.seatsLeft);
            arrowImg = itemView.findViewById(R.id.arrowImage);
            theflightID = itemView.findViewById(R.id.theflightID);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked
                }
            });

        }


    }


}