package com.example.flightindiza;
// card model to be populated with certain flight object details
public class CardModel {
    private String airportID;
    private String airport;
    private String deptTime;
    private String dateOf;
    private String duration;
    private String destination;
    private String arrivalTime;
    private String price;
    private String seatsLeft;
    private int arrowImg;

    public CardModel() {
    }

    public CardModel(String airportID, String airport, String deptTime, String dateOf, String duration, String destination, String arrivalTime, String price, String seatsLeft, int arrowImg) {
        this.airportID = airportID;
        this.airport = airport;
        this.deptTime = deptTime;
        this.dateOf = dateOf;
        this.duration = duration;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.seatsLeft = seatsLeft;
        this.arrowImg = arrowImg;
    }

    public String getAirportID() {
        return airportID;
    }

    public void setAirportID(String airportID) {
        this.airportID = airportID;
    }

    public String getAirport() {
        return airport;
    }

    public int getArrowImg() {
        return arrowImg;
    }

    public void setArrowImg(int arrowImg) {
        this.arrowImg = arrowImg;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getDeptTime() {
        return deptTime;
    }

    public void setDeptTime(String deptTime) {
        this.deptTime = deptTime;
    }

    public String getDateOf() {
        return dateOf;
    }

    public void setDateOf(String dateOf) {
        this.dateOf = dateOf;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeatsLeft() {


        return seatsLeft;
    }

    public void setSeatsLeft(String seatsLeft) {
        this.seatsLeft = seatsLeft;
    }
}
