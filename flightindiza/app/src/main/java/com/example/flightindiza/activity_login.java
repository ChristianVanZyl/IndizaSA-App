package com.example.flightindiza;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class activity_login extends AppCompatActivity {

    EditText email, password;
    String m, p;
    String verification = "denied";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.inputMaillogin);
        password = (EditText) findViewById(R.id.inputPass);

        FloatingActionButton login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                // get the text message on the text field
                m = email.getText().toString();
                p = password.getText().toString();
                // starting new thread to handle tasks of this activity
                new Thread(new ClientThread(m,p)).start();
            }
        });

        // link button to register activity
        Button linkToR = findViewById(R.id.linkToRegisterButton);
        linkToR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // executor service used to ensure that message is sent to server before activity switch
                ExecutorService service = Executors.newFixedThreadPool(1);

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

                Intent intent = new Intent(getApplicationContext(), activity_register.class);
                startActivity(intent);
            }
        });

    }

    public class ClientThread implements Runnable {
        String emailhere, passwordhere;
        String pageHome = "Home";
        String pageLogin = "Login";
        String userID;
        int uID;
        User loggedUser;

        ClientThread(String e, String p) {
            this.emailhere = e;
            this.passwordhere = p;
        }

        @Override
        public void run() {


            try {
                // data (user email and password input) is passed to server for verification
                // List array of string is used to pass data (need to pass an object)
                List<String> messages = new ArrayList<>();
                // add data to list array
                messages.add(emailhere);
                messages.add(passwordhere);
                // pass list array to server
                DataHandler.getObjectOutputStream().writeObject(messages);

                try {
                    // server sends verification result back in form of list object
                    List<String> authList = (List<String>) DataHandler.getObjectInputStream().readObject();;
                    for(int i = 0; i < authList.size(); i++){
                        Log.d("list", "" + authList.get(i));
                    }
                    verification = authList.get(0);
                    userID = authList.get(1);
                    uID = Integer.parseInt(userID);
                    Log.d("verifications", "" +verification);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Log.d("lets see", "" + verification);

                // if verification was successful, authList message would be equal to String message of approved
                if (verification.equalsIgnoreCase("approved")) {
                    Log.d("welcome", "welcome");
                    // send instructions to server
                    List<String> nextHome = new ArrayList<>();
                    nextHome.add(pageHome);
                    DataHandler.getObjectOutputStream().writeObject(nextHome);
                    // set user logged in for use during booking and profile
                    loggedUser = new User(uID, emailhere, passwordhere);
                    UserDataHandler.setCurrentUser(loggedUser);

                } else {
                    Log.d("fail", "failure");
                    List<String> nextFail = new ArrayList<>();
                    nextFail.add(pageLogin);
                    DataHandler.getObjectOutputStream().writeObject(nextFail);
                }

            } catch(IOException ex) {
                Log.d("error", "" + ex);
            }

            // updating the UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                        // if verification from server was approved, user is welcomed and activity switches to home
                    if(verification.equalsIgnoreCase("approved")){
                        Context context = getApplicationContext();
                        CharSequence welcome = "Welcome!";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, welcome, duration);
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(), activity_home.class);
                        startActivity(intent);

                    }else{
                        // if verification failed, user is informed through popup
                        Context context = getApplicationContext();
                        CharSequence failed = "Login failed, please try again!";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, failed, duration);
                        toast.show();
                    }
                }

            });

        }
    }
    }














