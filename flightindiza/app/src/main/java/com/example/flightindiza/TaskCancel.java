package com.example.flightindiza;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class TaskCancel implements Callable<List<String>> {
    String message = "success";
    String receive;
    List<String> tosendList = new ArrayList<>();
    List<String> thisList;

    public TaskCancel (List<String> a){

        this.thisList = a;
    }

    @Override
    public List<String> call() throws Exception {

        for(int i = 0; i < thisList.size(); i++){
            receive = thisList.get(i);
            tosendList.add(receive);
        }

        Log.d("check", "" + tosendList.get(0));
        Log.d("check", "" + tosendList.get(1));
        try {
            DataHandler.getObjectOutputStream().writeObject(tosendList);
            Log.d("sent", "message sent");
        } catch (Exception ex) {
            Log.d("error", "" + ex);
        }
        tosendList.add(message);
        return tosendList;
    }


}

