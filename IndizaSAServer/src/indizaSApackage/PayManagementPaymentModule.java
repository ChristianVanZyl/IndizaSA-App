package indizaSApackage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PayManagementPaymentModule {

    private final String url = "jdbc:mysql://localhost:3306/indizadb";
    private final String DBusername = "root";
    private final String DBpassword = "root";
    Connection conn;
    int userFlightID;
    private String email, password;
    private String creditStatus;
    int clientCredit;
    int bookingPrice;
    private int newBalance;
    private int reservID; 
    private int paymentID = 0;
    private int userCreditBefore = 0;
    private int userCreditAfter = 0;
    private int cancelledResID = 0;
    private int cancelledUserID = 0;
    private int cancelledflightPrice;
    private String paymentStatus = "Reversed";
    private String paymentStatusPaid = "Paid";
    PayManagementReceiptModule p = new PayManagementReceiptModule();
    
    public PayManagementPaymentModule() {
    }
    
    public String checkCredit(String usermail, String userpassword, int bookingP){
        bookingPrice = bookingP;
        email = usermail;
        password = userpassword;

        
                            try{ 
                             conn = DriverManager.getConnection(url, DBusername, DBpassword);
                                     // check whether user has enough credit in account
                             PreparedStatement st = conn.prepareStatement("SELECT userCredit, userID FROM user_table WHERE userEmail =? AND userPassword =?");
                             st.setString(1, email);
                             st.setString(2, password);
                             ResultSet rs = st.executeQuery();
                                          while(rs.next()){
                                              userFlightID = rs.getInt("userID");
                                              clientCredit = rs.getInt("userCredit");
                                          }    
                                 if(clientCredit < bookingPrice){
                                        creditStatus = "denied";
                                    }else{
                                        creditStatus = "approved";
                        
                                    
                                    }
         
                                }catch(SQLException ex){
                             System.out.println(ex);
                             }
                            // send result to booking operations
                            return creditStatus;
}
    

    public void adjustUserCredit(){
        
                        try{conn = DriverManager.getConnection(url, DBusername, DBpassword);
                         newBalance = clientCredit - bookingPrice;
                         p.businessBal = p.businessBal + bookingPrice;
                                              
                         PreparedStatement st1 = conn.prepareStatement("UPDATE user_table SET userCredit=? WHERE userEmail =? AND userPassword =?");
                         // statement 1 updates
                         st1.setInt(1, newBalance);
                         st1.setString(2, email);
                         st1.setString(3, password);
                                               
                         if (st1.executeUpdate() > 0) {
                            System.out.println("User's credit balance adjusted successfully");
                            System.out.println("The user's new credit balance is R" + newBalance);   
                            System.out.println("\n");
                       
                         } else {
                            System.out.println("Error in adjusting user credit balance");
                            System.out.println("\n");
                                }
                          }catch(SQLException ex){
                             System.out.println(ex);}
        
    }
    
    public void paymentUpdates(int resvID){
    
         reservID = resvID;
        
                    // if approved, we need to deduct the credit from user account
                    adjustUserCredit();
                     try{
                         conn = DriverManager.getConnection(url, DBusername, DBpassword);
                         // the payment system also needs to be updated on the database
                         PreparedStatement st2 = conn.prepareStatement("INSERT INTO payment_table SET reservationID=?, paymentStatus=?");                 
                     st2.setInt(1, reservID);
                     st2.setString(2,paymentStatusPaid);
	             if (st2.executeUpdate() > 0) {
                         System.out.println("Update to payment table successful"); 
                         System.out.println("\n");
	              } else {
                         System.out.println("Update to payment table failure");
                         System.out.println("\n");
                      }
                     }catch(SQLException ex){
                      System.out.println(ex);
                              }
}

    
    public void cancelPayment(int cancelReserv, int canceluser, int flightprice){
 
     // cancel payment which cancels reservation which cancels user and flight linked to it
     
     // flight seat still needs to be manually updated
     
   paymentID = 0;
   userCreditBefore = 0;
   userCreditAfter = 0;

   cancelledResID = cancelReserv;
   cancelledUserID = canceluser;
   cancelledflightPrice = flightprice;
     
     try {
                        conn = DriverManager.getConnection(url, DBusername, DBpassword);
         
                        // get userCredit before cancellation
                        PreparedStatement st1 = conn.prepareStatement("SELECT userCredit FROM user_table WHERE userID =?");
                           
                        st1.setInt(1, cancelledUserID);  
                        ResultSet res = st1.executeQuery();
                                while(res.next()){
                                 userCreditBefore = res.getInt("userCredit");
                                 }
                                
                    
                         // get paymentID        
                        PreparedStatement st2 = conn.prepareStatement("SELECT paymentID FROM payment_table, reservation_table WHERE reservation_table.reservationID = payment_table.reservationID AND reservation_table.reservationID =?");        
                         st2.setInt(1, cancelledResID);  
                         res = st2.executeQuery();
                                while(res.next()){
                                 paymentID = res.getInt("paymentID");
                                 }       
                         // use paymentID found to update paid to reversed status on payment_table  
                        System.out.println(paymentID);
                       PreparedStatement st3 = conn.prepareStatement("UPDATE payment_table SET paymentStatus=? WHERE paymentID =?");  
                       st3.setString(1, paymentStatus);  
                       st3.setInt(2, paymentID);
                        st3.execute();
                             
                       // reimburse user 
                        userCreditAfter = userCreditBefore + cancelledflightPrice;
                         System.out.println(userCreditAfter);
                        PreparedStatement st4 = conn.prepareStatement("UPDATE user_table SET userCredit=? WHERE userID =?");
                        st4.setInt(1, userCreditAfter);
                        st4.setInt(2, cancelledUserID);
                        st4.executeUpdate();
                     
                 
    }catch (SQLException e) {
      System.out.println("" + e);

    }
    }
}