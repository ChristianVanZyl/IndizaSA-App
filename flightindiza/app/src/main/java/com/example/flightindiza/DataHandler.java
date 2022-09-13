package com.example.flightindiza;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class DataHandler {

    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null ;

    public DataHandler() {

    }

    public static void setDataHandlers() throws IOException {

        try {

            objectOutputStream = new ObjectOutputStream(SocketHandler.getSocket().getOutputStream());
            objectInputStream = new ObjectInputStream(SocketHandler.getSocket().getInputStream());
        } catch (IOException ex) {
            Log.d("error", "" + ex);

        }
    }

    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public static ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }
}