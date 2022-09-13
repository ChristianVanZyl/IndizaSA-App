package com.example.flightindiza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class activity_profile extends AppCompatActivity {
    String emailpassed;
    String passwordpassed;
    TextView userFetchedName;
    TextView userFetchedSurname;
    TextView userFetchedEmail;
    TextView userFetchedCredit;
    User user = new User();
    ArrayList<RadioModel> radioModelArrayList = new ArrayList<>();
    List<String> messages = new ArrayList<>();
    RecyclerView radioView;
    RadiolistAdapter radioAdapter;
    LinearLayoutManager linearLayoutManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // add navbar to activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        radioView = findViewById(R.id.RVCard);
        radioAdapter = new RadiolistAdapter(this, radioModelArrayList);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        radioView.setLayoutManager(linearLayoutManager);
        radioView.setAdapter(radioAdapter);
        context = this;

        userFetchedName = (TextView)findViewById(R.id.detailsName);
        userFetchedSurname = (TextView)findViewById(R.id.detailsSurame);
        userFetchedEmail = (TextView)findViewById(R.id.detailsEmail);
        userFetchedCredit = (TextView)findViewById(R.id.detailsCredit);
        // new thread
        emailpassed = UserDataHandler.getCurrentUser().getUserEmail();
        passwordpassed = UserDataHandler.getCurrentUser().getUserPassword();


        // add data to list array
        messages.add(emailpassed);
        messages.add(passwordpassed);

        ExecutorService service = Executors.newFixedThreadPool(1);
        // future(promise) waiting on receiving confirmation from task that data was sent
        Future<List<String>> waiting2 =  service.submit(new TaskCancel(messages));
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

        new Thread(new activity_profile.ProfileThread()).start();

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



    public class ProfileThread implements Runnable {

        ProfileThread() {

        }

        @Override
        public void run() {

            // create user object


            // create gson object to transform string data (of user object) from server to object
            Gson gson = new Gson();
            String json = "";

            try {

                // get data from server - user object with all user details
                List<String> userList = (List<String>) DataHandler.getObjectInputStream().readObject();;
                for(int i = 0; i < userList.size(); i++){
                    // data in form of String, object was transformed to json String server-side
                    json = userList.get(i);
                    Log.d("check", "userlist done");
                    // with data on client-side, transform json String back to flight object
                    user = gson.fromJson(json, User.class);

                }}catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                RadioModel radObj;
                RadioModel radioObj;
                RadioModel radioMod;

                String json2 = "";

                List<RadioModel> radioObjList = new ArrayList<>();

                try {
                    // get data from server - flight objects with all flight details
                    List<String> radioList = (List<String>) DataHandler.getObjectInputStream().readObject();;
                    for(int i = 0; i < radioList.size(); i++){
                        // data in form of String, object was transformed to json String server-side
                        json2 = radioList.get(i);
                        // with data on client-side, transform json String back to flight object
                        radioMod = gson.fromJson(json2, RadioModel.class);
                        // add object to Flight object array
                        radioObjList.add(radioMod);
                    }


                    for(int i = 0; i < radioObjList.size(); i++){
                        // use created flight object array to populate card objects
                        radioObj = radioObjList.get(i);
                        // on each loop, new card model is created
                        radObj = new RadioModel();
                        // card model created data added, populated with data from flight object from server
                        radObj.setFlightID(String.valueOf((radioObj.getFlightID())));
                        radObj.setDestination(radioObj.getDestination());
                        radObj.setDateOf(radioObj.getDateOf());
                        radObj.setReservationID(String.valueOf((radioObj.getReservationID())));

                        radioModelArrayList.add(radObj);
                    }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            Log.d("check", "runnable done") ;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                     userFetchedName.setText("Name: " + user.getUserName());
                     userFetchedSurname.setText("Surname: " + user.getUserSurname());
                     userFetchedEmail.setText("Email address: " + user.getUserEmail());
                     userFetchedCredit.setText("Available credit: R" + user.getUserCredit());
                    radioAdapter.setContext(context);

                    radioAdapter.setRadioModelArrayList(radioModelArrayList);

                    radioAdapter.notifyDataSetChanged();

                }

            });

        }


    }




}