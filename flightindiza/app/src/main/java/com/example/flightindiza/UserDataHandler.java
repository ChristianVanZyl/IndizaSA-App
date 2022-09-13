package com.example.flightindiza;


public class UserDataHandler {

    // makes User static object to access across application without having to send to each activity
    // object is only initialized after login approved
    private static User currentUser;

    public UserDataHandler() {

    }


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserDataHandler.currentUser = currentUser;
    }
}