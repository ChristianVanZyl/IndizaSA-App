package com.example.flightindiza;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button switchToLogin = findViewById(R.id.loginButtonMain);
        Button switchToRegister = findViewById(R.id.registerButtonMain);

        try{
            switchToRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // service to ensure that backend data is fetched and processed before
                    ExecutorService service = Executors.newFixedThreadPool(1);
                    // future(promise) waiting on receiving confirmation from task that data was sent
                    Future<String> waiting =  service.submit(new Task("Register"));
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
                    switchToRegisterActivity();
                }
            });

        }catch(Exception e){
            Context context = getApplicationContext();
            CharSequence serverMsg = "Server seems to be down!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, serverMsg, duration);
            toast.show();
        }




        // login button switching to login page
        switchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // service to ensure that backend data is fetched and processed before
                ExecutorService service = Executors.newFixedThreadPool(1);
                // future(promise) waiting on receiving confirmation from task that data was sent
                Future<String> waiting =  service.submit(new Task("Login"));
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
                switchToLoginActivity();
            }
        });
    }




    public void switchToLoginActivity() {

        Intent intent = new Intent(this, activity_login.class);
        startActivity(intent);

    }

    public void switchToRegisterActivity() {

        Intent intent = new Intent(this, activity_register.class);
        startActivity(intent);

    }


}











