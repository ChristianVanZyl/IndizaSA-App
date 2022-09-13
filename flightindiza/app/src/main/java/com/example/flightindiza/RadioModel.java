package com.example.flightindiza;

public class RadioModel {
    private String flightID;
    private String reservationID;
    private String destination;
    private String date;

    public RadioModel() {
    }

    public RadioModel(String flightID, String reservationID, String destination, String date) {
        this.flightID = flightID;
        this.reservationID = reservationID;
        this.destination = destination;
        this.date = date;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateOf() {
        return date;
    }

    public void setDateOf(String date) {
        this.date = date;
    }
}




