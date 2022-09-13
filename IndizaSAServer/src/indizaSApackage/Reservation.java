
package indizaSApackage;

public class Reservation {
 
 private int flightID;
 private int reservationID;
 private String destination;
 private String date;
    
 public Reservation() {
     
    }

    public Reservation(int flightID, int reservationID, String destination, String date) {
        this.flightID = flightID;
        this.reservationID = reservationID;
        this.destination = destination;
        this.date = date;
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
   
}
