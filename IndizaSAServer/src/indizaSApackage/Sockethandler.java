/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indizaSApackage;

import java.net.Socket;

/**
 *
 * @author chris
 */
public class Sockethandler {

    private static Socket socket;

    public Sockethandler() {

    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        Sockethandler.socket = socket;
    }

    

}