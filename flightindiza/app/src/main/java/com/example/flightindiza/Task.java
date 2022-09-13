package com.example.flightindiza;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

// callable from Main Activity to send message to server, indicating what data to load
public class Task implements Callable<String> {
    String msg;
    String status;

    public Task(String msg){
        this.msg = msg;
    }

    @Override
    public String call() {

        try {
            SocketHandler.setSocket();
            List<String> messages = new ArrayList<>();
            messages.add(msg);
            DataHandler.setDataHandlers();
            DataHandler.getObjectOutputStream().writeObject(messages);
            Log.d("sent", "message sent");
            status = "completed";

        } catch (Exception ex) {
            Log.d("error", "" + ex);
        }
        return status;
    }


}
