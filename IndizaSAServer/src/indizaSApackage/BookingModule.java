package indizaSApackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingModule {
    
    private final String url = "jdbc:mysql://localhost:3306/indizadb";
    private final String DBusername = "root";
    private final String DBpassword = "root";
    Connection conn;
    
    private String ussID, email, password;
    private int usID;
    private String bookingPrice;
    private int bookingP;
    private int bookingFlightID;
    private String bookingFID;
    private String bookingSeats;
    private int bookingSeatsL;
    private int newSeatsLeft;
    private String creditStatus;
    PayManagementPaymentModule paymentModule = new PayManagementPaymentModule();
    PayManagementReceiptModule receiptModule = new PayManagementReceiptModule();
    private int reservationID;
    private int flightTocancelID;
    private int userIDForCancel;
    private int reservationIDtoCancel;
    private int flightpriceCancel;
    private int numSeatsLeftNew;
    private int oldSeatnum;
    String verification = "Done";
    private final String statusUpdate = "Cancelled";
    private final String statUpdate = "active";
               
    public BookingModule() {
    }
    
    public void bookingOperation(){
    
                    try{ 
                        System.out.println("\n");
                        conn = DriverManager.getConnection(url, DBusername, DBpassword);
                                    
                                    // data being received by server
                                    List<String> bookingList = (List<String>)Datahandler.getObjectInputStream().readObject();
                                    ussID = bookingList.get(0);
                                    usID = Integer.parseInt(ussID);
                                    email = bookingList.get(1);
                                    password = bookingList.get(2);      
                                    bookingPrice = bookingList.get(3);
                                    bookingP = Integer.parseInt(bookingPrice);  
                                    bookingFID = bookingList.get(4);
                                    bookingFlightID = Integer.parseInt(bookingFID); 
                                    bookingSeats = bookingList.get(5);
                                    bookingSeatsL = Integer.parseInt(bookingSeats); 
                                   
                                    System.out.println("Checking credit status");
                                    creditStatus = paymentModule.checkCredit(email, password, bookingP);
                                        
                                         // informing client of result
                                        System.out.println("Server sending credit result: " + creditStatus);     
                                        // data being sent to client
                                        List<String> verificationList = new ArrayList<>();   
                                        verificationList.add(creditStatus);
                                        // data written
                                        Datahandler.getObjectOutputStream().writeObject(verificationList); 
                                        
                                        
                                     if(creditStatus.equalsIgnoreCase("denied")){
                                         System.out.println("\n");     
                                         System.out.println("Denied the booking due to insufficient funds");
                                          }else{
                                            System.out.println("\n");
                                             // credit status approved, we need to update the reservation table, payments and seats left on the flight
                                            System.out.println("Approved credit and booking");
                                            
                                             // update the reservation table
                                             updateReservationTable();
                                             
                                             
                                             // to update payments, we need to now find the newly generated reservation ID from the reservation table
                                             PreparedStatement stat = conn.prepareStatement("SELECT reservationID FROM reservation_table WHERE userID =? AND flightID =?");
                                             stat.setInt(1, paymentModule.userFlightID);
                                             stat.setInt(2, bookingFlightID);
                                             ResultSet res = stat.executeQuery();
                                             while(res.next()){
                                              reservationID = res.getInt("reservationID");
                                              }
                             
                                             // update payments using reservation ID found
                                             paymentModule.paymentUpdates(reservationID);
                                             receiptModule.generateInvoiceForUser(usID, bookingFlightID, bookingP, reservationID);
                                             // adjust the number of seats left available
                                             // for this we need to find number of seats left first         
                                             PreparedStatement state = conn.prepareStatement("SELECT numSeatsLeft FROM flight_table WHERE flightID =?");
                                             state.setInt(1, bookingFlightID);  
                                             res = state.executeQuery();
                                             while(res.next()){
                                              bookingSeatsL = res.getInt("numSeatsLeft");
                                              }
                                            
                                              newSeatsLeft = bookingSeatsL - 1;
                                              System.out.println("\n");
                                              System.out.println("Now there are " + newSeatsLeft + " seats left on the flight.");
                            
                                              PreparedStatement st2 = conn.prepareStatement("UPDATE flight_table SET numSeatsLeft=? WHERE flightID =?");
                                           
                                     
                                               // statement 2 updates
                                               st2.setInt(1, newSeatsLeft);
                                               st2.setInt(2, bookingFlightID);
                                                if (st2.executeUpdate() > 0) {
						System.out.println("Number of seats left on flight adjusted successfully");
                                                  
                                                } else {
                                                 System.out.println("Error in adjusting number of seats left on flight");
                                                 }
                                                
                                           
                                          }
                                    // send result result
                                    }catch(SQLException | IOException | ClassNotFoundException ex){
                             System.out.println(ex);
                             }
    
    }
    
    public void updateReservationTable(){
        
        try{
        PreparedStatement st3 = conn.prepareStatement("INSERT INTO reservation_table SET userID=?, flightID=?, statusR=?");
                                               // statement 3 insert
                                               st3.setInt(1, paymentModule.userFlightID);
                                               st3.setInt(2, bookingFlightID);
                                               st3.setString(3, statUpdate);
                                                if (st3.executeUpdate() > 0) {
						System.out.println("Reservation insert completed successfully");
                                                  System.out.println("\n");
                                                } else {
                                                 System.out.println("Error in adding reservation");
                                                }
                                          }catch(SQLException ex){
                             System.out.println(ex);
                             }
           
    }
    
 
    
    public void cancelReservation(){
   
      // data being received by server
     // need to fetch data from client first 
      try{     
           List<String> cancelList = (List<String>)Datahandler.getObjectInputStream().readObject();
           flightTocancelID = Integer.parseInt(cancelList.get(0));
           userIDForCancel = Integer.parseInt(cancelList.get(1));

      
           conn = DriverManager.getConnection(url, DBusername, DBpassword);
           // get reservation ID of reservation applicable by using user ID and flight ID together
           PreparedStatement st1 = conn.prepareStatement("SELECT reservationID FROM reservation_table WHERE userID =? AND flightID =?");
                                             st1.setInt(1, userIDForCancel);
                                             st1.setInt(2, flightTocancelID);
                                             ResultSet res = st1.executeQuery();
                                             while(res.next()){
                                              reservationIDtoCancel = res.getInt("reservationID");
                                              }
           // get flight price and number of seats left, need variable values to update them                 
           PreparedStatement st2 = conn.prepareStatement("SELECT price,numSeatsLeft FROM flight_table WHERE flightID =?");
                                             st2.setInt(1, flightTocancelID );
                                        res = st2.executeQuery();
                                             while(res.next()){
                                              flightpriceCancel = res.getInt("price");
                                              oldSeatnum = res.getInt("numSeatsLeft");
                                              }
                                      
                                         numSeatsLeftNew =   oldSeatnum + 1;  
           // update seats left                        
           PreparedStatement st3 = conn.prepareStatement("UPDATE flight_table SET numSeatsLeft=? WHERE flightID =?");
                                        st3.setInt(1, numSeatsLeftNew );                                  
                                        st3.setInt(2, flightTocancelID );
                                        st3.executeUpdate();
           // update reservation to cancelled
           PreparedStatement st4 = conn.prepareStatement("UPDATE reservation_table SET statusR=? WHERE reservationID =?");
                                       st4.setString(1, statusUpdate);                                  
                                       st4.setInt(2, reservationIDtoCancel );
                                       st4.executeUpdate();
           // cancel payment
           // pass variables needed for adjustments in payment system
           paymentModule.cancelPayment(reservationIDtoCancel, userIDForCancel, flightpriceCancel);
           System.out.println("Data for cancellation passed.");
           System.out.println("\n");
           // generate receipt
           receiptModule.generateInvoiceForUser(userIDForCancel, flightTocancelID, flightpriceCancel, reservationIDtoCancel);
                        // data being sent to client
                      List<String> verificationList = new ArrayList<>();   
                      verificationList.add(verification);
                       // data written
                      Datahandler.getObjectOutputStream().writeObject(verificationList);  
        }catch(IOException | ClassNotFoundException | SQLException ex){
           System.out.println(ex);
     }
    }
 
}
