package com.example.flightindiza;


import java.io.IOException;
import java.net.Socket;
// makes socket static object to access across application without having to send to each activity
public class SocketHandler {

    private static Socket socket;


    public SocketHandler() {

    }

    public static void setSocket() throws IOException {
        socket = new Socket("192.168.1.113", 8000);
    }

    public static Socket getSocket() {
        return SocketHandler.socket;

    }

}