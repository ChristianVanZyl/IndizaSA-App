package com.example.flightindiza;

public class User {

    private int id;
    private String userName, userSurname, userEmail, userPassword;
    private int userCredit;

    public User() { }

    public User(int id, String userEmail, String userPassword){
        this.id = id;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public User(int id, String userName, String userSurname, String userEmail, String userPassword, int userCredit) {
        this.id = id;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userCredit = userCredit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(int userCredit) {
        this.userCredit = userCredit;
    }



}

