package indizaSApackage;


public class Flight {
    
    private int flightID;
    private String destination;
    private int price;
    private String dateOf;
    private String deptTime;
    private String duration;
    private String arrivalTime;
    private int numSeatsLeft;
    private String airport;

    public Flight() {
    }

    public Flight(int flightID, String destination, int price, String dateOf, String deptTime, String duration, String arrivalTime, int numSeatsLeft, String airport) {
        this.flightID = flightID;
        this.destination = destination;
        this.price = price;
        this.dateOf = dateOf;
        this.deptTime = deptTime;
        this.duration = duration;
        this.arrivalTime = arrivalTime;
        this.numSeatsLeft = numSeatsLeft;
        this.airport = airport;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateOf() {
        return dateOf;
    }

    public void setDateOf(String dateOf) {
        this.dateOf = dateOf;
    }

    public String getDeptTime() {
        return deptTime;
    }

    public void setDeptTime(String deptTime) {
        this.deptTime = deptTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getNumSeatsLeft() {
        return numSeatsLeft;
    }

    public void setNumSeatsLeft(int numSeatsLeft) {
        this.numSeatsLeft = numSeatsLeft;
    }
    
    
    
    
    
    
    
}
