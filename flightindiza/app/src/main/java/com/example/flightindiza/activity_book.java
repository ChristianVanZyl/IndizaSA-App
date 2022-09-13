package com.example.flightindiza;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class activity_book extends AppCompatActivity {
    String airportID;
    String seatsLeft;
    String airport;
    String dateOf;
    String deptTime;
    String duration;
    String destination;
    String arrivalTime;
    String price;
    String flighID;
    String direct;
    TextView airportF, dateOfF, timeOfd, durationOfF, destinationOfF, arrivalOfF, priceOfF, fID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        // add navbar to activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // get text fields to be filled
        airportF = (TextView) findViewById(R.id.airportFrom);
        dateOfF = (TextView) findViewById(R.id.dateOfFlight);
        timeOfd = (TextView) findViewById(R.id.timeOfdept);
        durationOfF = (TextView) findViewById(R.id.durationOfFlight);
        destinationOfF = (TextView) findViewById(R.id.destinationOfFlight);
        arrivalOfF = (TextView) findViewById(R.id.arrivalOfFlight);
        priceOfF = (TextView) findViewById(R.id.priceOfFlight);
        fID = (TextView)findViewById(R.id.flighID);

        // get data stored with intent from previous activity - thus clicked card info
        airportID = getIntent().getStringExtra("airportID");
        seatsLeft = getIntent().getStringExtra("seats");
        airport = getIntent().getStringExtra("airport");
        dateOf = getIntent().getStringExtra("dateOf");
        deptTime = getIntent().getStringExtra("deptTime");
        duration = getIntent().getStringExtra("duration");
        destination = getIntent().getStringExtra("destination");
        arrivalTime = getIntent().getStringExtra("arrivalTime");
        price = getIntent().getStringExtra("price");
        flighID = getIntent().getStringExtra("fID");
        // set text fields to the data fetched from previous activity
        airportF.setText(airport);
        dateOfF.setText(dateOf);
        timeOfd.setText(deptTime);
        durationOfF.setText(duration);
        destinationOfF.setText(destination);
        arrivalOfF.setText(arrivalTime);
        priceOfF.setText("R" + price);
        fID.setText("" +flighID);

        Button bookButton = findViewById(R.id.bookButton);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("happens", "this happens");
                new Thread(new activity_book.BookingThread(price, airportID, seatsLeft)).start();
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direct = "Home";
                sendMessageServerContext(direct);
            }
        });
    }

    // add menu to navbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    // add navbar item actions i.e. logout, switch to home, switch to profile
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT)
                        .show();
                ExecutorService service = Executors.newFixedThreadPool(1);
                Future<String> waiting =  service.submit(new Task("Login"));
                String s = "";
                try {
                    s = waiting.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("check", "" + s);
                // logging out by closing active tasks
                this.finishAffinity();
                break;

            case R.id.action_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT)
                        .show();
                service = Executors.newFixedThreadPool(1);
                waiting =  service.submit(new Task("Home"));
                s = "";
                try {
                    s = waiting.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("check", "" + s);
                // changing to home activity
                Intent intent = new Intent(this, activity_home.class);
                startActivity(intent);
                break;

            case R.id.action_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT)
                        .show();
                service = Executors.newFixedThreadPool(1);
                waiting =  service.submit(new Task("Profile"));
                s = "";
                try {
                    s = waiting.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("check", "" + s);
                //  changing to profile activity
                intent = new Intent(this, activity_profile.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return true;
    }

    // sends server application context message and switches activity
    public void sendMessageServerContext(String msg){
         String passedMsg = msg;
         ExecutorService service = Executors.newFixedThreadPool(1);

        if(passedMsg.equalsIgnoreCase("Home")){

            Future<String> waiting =  service.submit(new Task("Home"));
            String s = "";
            try {
                s = waiting.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("check", "" + s);

            Intent intent = new Intent(getApplicationContext(), activity_home.class);
            startActivity(intent);

        }else{

            service = Executors.newFixedThreadPool(1);
            Future<String> secondWait =  service.submit(new Task("Profile"));
            String s = "";
            try {
                s = secondWait.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("check", "" + s);

            Intent intent = new Intent(getApplicationContext(), activity_profile.class);
            startActivity(intent);

        }

    }

    public class BookingThread implements Runnable {
        String verification;
        // get logged user details
        int usID = UserDataHandler.getCurrentUser().getId();
        String ussID = String.valueOf(usID);
        String email = UserDataHandler.getCurrentUser().getUserEmail();
        String password = UserDataHandler.getCurrentUser().getUserPassword();
        // get price of ticket passed from booking activity
        String pricehere;
        String airportIDhere;
        String seatsLefthere;

        BookingThread(String price, String aID, String sL ) {
            this.pricehere = price;
            this.airportIDhere = aID;
            this.seatsLefthere = sL;
        }

        @Override
        public void run() {

            try {

                // data of current user (user id, email, password) is passed to server
                // server can then check status of funds
                // List array of string is used to pass data (need to pass an object)
                List<String> messages = new ArrayList<>();
                // add data to list array
                messages.add(ussID);
                messages.add(email);
                messages.add(password);
                messages.add(pricehere);
                messages.add(airportIDhere);
                messages.add(seatsLefthere);
                // pass list array to server
                DataHandler.getObjectOutputStream().writeObject(messages);

                // server sends verification result back in form of list object
                List<String> fundCheck = (List<String>) DataHandler.getObjectInputStream().readObject();;

                for(int i = 0; i < fundCheck.size(); i++){
                    Log.d("list", "" + fundCheck.get(i));
                    // first and only message stored in list array from server accessed by index posistion 0
                    verification = fundCheck.get(0);
                    Log.d("verifications", "" +verification);
                }
                     Log.d("lets see", "" + verification);
                        // if verification was successful, authList message would be equal to String message of approved
                    if (verification.equalsIgnoreCase("approved")) {
                        Log.d("funds", "sufficient funds");

                    } else {
                        Log.d("funds", "insufficient funds");

                    }
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
            // updating the UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity_book.this);
                    if(verification.equalsIgnoreCase("approved")){

                        builder.setMessage("You have successfully booked a ticket!")
                                .setCancelable(true)
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.dismiss();
                                        direct = "Profile";
                                        sendMessageServerContext(direct);

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getApplication().getResources().getColor(R.color.blue));
                    }else{

                        builder.setMessage("You have insufficient funds, please mail proof of payment for account to be credited")
                                .setCancelable(true)
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.dismiss();
                                        direct = "Home";
                                        sendMessageServerContext(direct);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getApplication().getResources().getColor(R.color.blue));
                    }

            }});
        }}

}