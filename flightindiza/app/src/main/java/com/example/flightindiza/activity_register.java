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



public class activity_register extends AppCompatActivity {
    EditText regName, regSurname, regEmail, regPassword;
    String n, s, e, p;
    String verification = "failed";
    Context context;
    // input test boolean;
    boolean testInputs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regName = (EditText) findViewById(R.id.inputName);
        regSurname = (EditText) findViewById(R.id.inputSurname);
        regEmail = (EditText) findViewById(R.id.inputMail);
        regPassword = (EditText) findViewById(R.id.inputPW);

        FloatingActionButton register = findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                testInputs = false;
                // get the text message on the text field
                n = regName.getText().toString();
                if(!validateUserName(n)){
                    context = getApplicationContext();
                    CharSequence msg = "Please do not leave any field empty";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, msg, duration);
                    toast.show();
                    testInputs = false;
                }else{
                    testInputs = true;
                }
                s = regSurname.getText().toString();
                if(!validateUserSurname(s)){
                    context = getApplicationContext();
                    CharSequence msg = "Please do not leave any field empty";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, msg, duration);
                    toast.show();
                    testInputs = false;
                }else{
                    testInputs = true;
                }

                e = regEmail.getText().toString();
                if(!validateEmail(e)){
                    context = getApplicationContext();
                    CharSequence msg = "Please enter a valid email address";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, msg, duration);
                    toast.show();
                    testInputs = false;
                }else{
                    testInputs = true;
                }

                p = regPassword.getText().toString();
                if(! validatePassword(p)){
                    context = getApplicationContext();
                    CharSequence msg = "Please enter a valid password, using one lowercase and uppercase letter, at least one number and eight characters";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, msg, duration);
                    toast.show();
                    testInputs = false;
                }else{
                    testInputs = true;
                }
                if(testInputs) {
                    // new thread to complete tasks of this activity
                    new Thread(new activity_register.ClientThread(n, s, e, p)).start();
                }

            }
        });

        Button linkToL = findViewById(R.id.linkToLogin);
        linkToL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // executor service used to ensure that message is sent to server before activity switch
                ExecutorService service = Executors.newFixedThreadPool(1);

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

                Intent intent = new Intent(getApplicationContext(), activity_login.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validateUserName(String name) {
        String test = name;

        if (test.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    private Boolean validateUserSurname(String surname) {
        String test = surname;

        if (test.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    private Boolean validateEmail(String email) {
        String test = email;
        String emailTest = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (test.isEmpty()) {
            return false;
        } else if (!test.matches(emailTest)) {
            return false;
        } else {
            return true;
        }
    }

    private Boolean validatePassword(String password) {
        String test = password;
        String passwordVal = "^" +
                // minimum one lowercase
                "(?=.*[a-z])" +
                // minimum one uppercase
                "(?=.*[A-Z])" +
                // minimum one number
                "(?=.*[0-9])" +
                // no white space allowed
                "(?=\\S+$)" +
                // minimum 8 characters
                ".{8,}" +
                "$";

        if (test.isEmpty()) {
            return false;
        } else if (!test.matches(passwordVal)) {
            return false;
        } else {
            return true;
        }
    }

    public class ClientThread implements Runnable {
        String namehere, surnamehere, emailhere, passwordhere;
        String pageLogin = "Login";
        String pageRegister = "Register";

        ClientThread(String n, String s, String e, String p) {
            this.namehere = n;
            this.surnamehere = s;
            this.emailhere = e;
            this.passwordhere = p;
        }

        @Override
        public void run() {

            try {
                // data (username, surname email and password input) is passed to server for adding to server
                // List array of string is used to pass data (need to pass an object)
                List<String> messages = new ArrayList<>();
                messages.add(namehere);
                messages.add(surnamehere);
                messages.add(emailhere);
                messages.add(passwordhere);
                DataHandler.getObjectOutputStream().writeObject(messages);
                // server sends result of registration, success or failure, back in form of list object
                try {
                    List<String> authList = (List<String>) DataHandler.getObjectInputStream().readObject();;
                    for(int i = 0; i < authList.size(); i++){
                        Log.d("list", "" + authList.get(i));
                    }
                    verification = authList.get(0);
                    Log.d("verifications", "" +verification);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Log.d("lets see", "" + verification);


                if (verification.equalsIgnoreCase("success")) {
                    Log.d("welcome", "welcome");
                    // send instructions to server
                    List<String> nextLogin = new ArrayList<>();
                    nextLogin.add(pageLogin);
                    DataHandler.getObjectOutputStream().writeObject(nextLogin);

                } else {
                    Log.d("fail", "failure");
                    List<String> nextFail = new ArrayList<>();
                    nextFail.add(pageRegister);
                    DataHandler.getObjectOutputStream().writeObject(nextFail);
                }

            } catch(IOException ex) {
                Log.d("error", "" + ex);
            }

            // updating the UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(verification.equalsIgnoreCase("success")){
                        Context context = getApplicationContext();
                        CharSequence msg = "Registration successful!";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, msg, duration);
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(), activity_login.class);
                        startActivity(intent);

                    }else{

                        Context context = getApplicationContext();
                        CharSequence failed = "Registration failed, please try again!";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, failed, duration);
                        toast.show();
                    }
                }

            });

        }
    }


}