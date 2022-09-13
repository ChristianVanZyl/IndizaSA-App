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
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class activity_home extends AppCompatActivity {

    // create recyclerview to display cards as list
    RecyclerView cardRV;
    // created an array to fill with cardmodel objects after they have been populated
    ArrayList<CardModel> cardModelArrayList = new ArrayList<>();
    // card adapter to
    CardAdapter cardAdapter;
    // create layout manager for created cardmodel array
    LinearLayoutManager linearLayoutManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // add navbar to activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cardRV = findViewById(R.id.RVCard);
        cardAdapter = new CardAdapter(this, cardModelArrayList);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        cardRV.setLayoutManager(linearLayoutManager);
        cardRV.setAdapter(cardAdapter);
        context = this;
        new Thread(new activity_home.HomeThread()).start();

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
                waiting = service.submit(new Task("Profile"));
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



    public class HomeThread implements Runnable {

        HomeThread() {

        }

        @Override
        public void run() {
            // create cardmodel object
            CardModel cardObj;
            // create gson object to transform string data (of flight objects) from server to objects
            Gson gson = new Gson();
            String json = "";
            // flightmodel object created to fill with flightmodel object from server
            Flight flightModel;
            Flight obj;
            // list created to fill with flightmodel objects
            List<Flight> flightObjList = new ArrayList<>();

            try {
                // get data from server - flight objects with all flight details
                List<String> flightList = (List<String>) DataHandler.getObjectInputStream().readObject();;
                for(int i = 0; i < flightList.size(); i++){
                    // data in form of String, object was transformed to json String server-side
                    json = flightList.get(i);
                    // with data on client-side, transform json String back to flight object
                    flightModel = gson.fromJson(json, Flight.class);
                    // add object to Flight object array
                    flightObjList.add(flightModel);
                }


                for(int i = 0; i < flightObjList.size(); i++){
                    // use created flight object array to populate card objects
                    obj = flightObjList.get(i);
                    // on each loop, new card model is created
                    cardObj = new CardModel();
                    // card model created data added, populated with data from flight object from server
                    cardObj.setAirportID(String.valueOf((obj.getFlightID())));
                    cardObj.setAirport(obj.getAirport());
                    cardObj.setDeptTime(obj.getDeptTime());
                    cardObj.setDateOf(obj.getDateOf());
                    cardObj.setDuration(obj.getDuration());
                    cardObj.setDestination( obj.getDestination());
                    cardObj.setArrivalTime(obj.getArrivalTime());
                    cardObj.setPrice("" + obj.getPrice());
                    cardObj.setSeatsLeft(obj.getNumSeatsLeft() + "");
                    cardObj.setArrowImg(R.drawable.rightarrow);
                    // object added to cardmodel array for use by cardadapter
                    cardModelArrayList.add(cardObj);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cardAdapter.setContext(context);
                    // cardadapter set to use cardmodelarraylist
                    cardAdapter.setCardModelArrayList(cardModelArrayList);
                    // after cardmodelarraylist has been populated with server data, cardadapter is notified of changes and cardModelArraylist now displays data in cards form
                    cardAdapter.notifyDataSetChanged();

                }

            });

        }


    }


}





