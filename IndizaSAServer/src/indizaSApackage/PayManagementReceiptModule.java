package indizaSApackage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PayManagementReceiptModule {
    
    private final String url = "jdbc:mysql://localhost:3306/indizadb";
    private final String DBusername = "root";
    private final String DBpassword = "root";
    Connection conn;
    private int userIDPassed;
    private String heading = "Transactions";
    private String heading2 = "Debit";
    private String heading3 = "Credit";
    private String heading4 = "Balance";
    private String paymentS = "Reversed";
    private String paymentP = "Paid";
    private String empty = "R0";
    private String empty2 = "......";
    private int revPrice;
    private int invoiceID;
    private int pPrice = 0;
    private int idOfpay = 0;
    private int debit = 0;
    private int credit = 0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    LocalDateTime timenow = LocalDateTime.now();  
    int businessBal = 1000000;  
    List<Integer>  invoiceRarray = new ArrayList<>();
    List<Integer>  invoiceParray = new ArrayList<>();
    List<Integer>  pArray = new ArrayList<>(); 
    List<Integer>  rArray = new ArrayList<>();
    private String heading6 = "User ID";
    private String heading7 = "Flight ID";
    private String heading8 = "Airport";
    private String heading9 = "Destination";
    private String heading10 = "Flight Price";
    private String heading11 = "Credit Balance";
    private int passUID;
    private int passFID;
    private String passA;
    private String passD;
    private int passCredit;
    private int passPrice;
    private int passID;
   
    
    public void cashDisbursementReport(){
    
        // get all transactions paid for
        try{
             conn = DriverManager.getConnection(url, DBusername, DBpassword);
        PreparedStatement st = conn.prepareStatement("SELECT payment_table.paymentID, reservation_table.reservationID, flight_table.price FROM payment_table, reservation_table, flight_table WHERE reservation_table.reservationID = payment_table.reservationID AND reservation_table.flightID = flight_table.flightID AND payment_table.paymentStatus=?");
                             st.setString(1, paymentP);
                        
                             ResultSet rs = st.executeQuery();
                                          while(rs.next()){
                                              invoiceID = rs.getInt("paymentID");
                                              pPrice = rs.getInt("price");
                                              invoiceParray.add(invoiceID);
                                              pArray.add(pPrice);
                                        
                                          }    
 }catch(SQLException ex){
                      System.out.println(ex);
                              }
    // get all transactions reversed    
    try{
         conn = DriverManager.getConnection(url, DBusername, DBpassword);
        PreparedStatement st = conn.prepareStatement("SELECT payment_table.paymentID, reservation_table.reservationID, flight_table.price FROM payment_table, reservation_table, flight_table WHERE reservation_table.reservationID = payment_table.reservationID AND reservation_table.flightID = flight_table.flightID AND payment_table.paymentStatus=?");
                             st.setString(1, paymentS);
                          
                             ResultSet rs = st.executeQuery();
                                          while(rs.next()){
                                              invoiceID = rs.getInt("paymentID");
                                              revPrice = rs.getInt("price");
                                              invoiceRarray.add(invoiceID);
                                              rArray.add(revPrice);
                                       
                                          }    
 }catch(SQLException ex){
                      System.out.println(ex);
                              }
    // report containing all transactions paid and reversed
        try {
      FileWriter writer = new FileWriter("C:\\Users\\chris\\Documents\\NetBeansProjects\\IndizaSAServer\\src\\paymentGeneratedFiles\\CashDisbursementReport" + ".txt");
      writer.write("Date report generated: " + formatter.format(timenow));
      writer.write(String.format("%n"));
      writer.write(String.format("%s\t%s\t%s\t%s", heading, heading2, heading3, heading4));
      writer.write(String.format("%n"));   
      
                for(int i = 0; i < pArray.size(); i++)
                {
                        idOfpay = invoiceParray.get(i);
                        credit = pArray.get(i);
                        businessBal = businessBal + credit;
                 writer.write(
                         String.format("%s\t%s\t%s\t%s" + String.format("%n") + String.format("%n"), "Invoice no:" + idOfpay, empty, "R" + credit, "R" +businessBal)
                         
                 );
                
                }
                for(int j = 0; j < rArray.size(); j++){
                
                        idOfpay = invoiceRarray.get(j);
                        debit = rArray.get(j);
                        businessBal = businessBal - debit;
                 writer.write(String.format("%s\t%s\t%s\t%s" + String.format("%n") + String.format("%n"), "Invoice no:" + idOfpay, "R" + debit, empty, "R" +businessBal));
                }
                writer.write(String.format("%s\t%s\t%s" + String.format("%n") + String.format("%n"), empty2, empty2, "Final Balance: R" + businessBal));
                
      writer.close();
      System.out.println("File written");
    } catch (IOException e) {
      System.out.println("An error occurred.");

    }
  }
        

    public void transactionReportForUser(String idquery){
        String queryUserTransac = idquery;
     
        int minimum = 1;
        int maximum = 10000;
        
      //Generate random int value from 50 to 100 
     
      int rndom = (int)Math.floor(Math.random()*(maximum-minimum+1)+minimum);
      int idNeeded = Integer.parseInt(queryUserTransac);
      String insert = idquery + "_reportNumber_" + rndom + ".txt";
        

    try{
          conn = DriverManager.getConnection(url, DBusername, DBpassword);
          PreparedStatement st1 = conn.prepareStatement("SELECT invoicefile FROM transactionrecords_table WHERE userID =? INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/QueryOutput" + insert + "' FIELDS TERMINATED BY ','");
          
                                        st1.setInt(1, idNeeded);
                                       
                                        st1.executeQuery();
                                             
          
    }catch(SQLException ex){
            System.out.println(ex);
        }
    }
   
    public void generateInvoiceForUser(int pasUID, int pasFID, int passPric, int ress){
       
        passUID = pasUID;
        passID = ress;
        passFID = pasFID;
        passPrice = passPric;
    
        try{
          conn = DriverManager.getConnection(url, DBusername, DBpassword);
          PreparedStatement st1 = conn.prepareStatement("SELECT flight_table.destination, airport_table.airportName FROM flight_table, airport_table WHERE flight_table.airportID = airport_table.airportID AND flightID =?");
                                             st1.setInt(1, passFID);
                                             ResultSet res = st1.executeQuery();
                                             while(res.next()){
                                              passD = res.getString("destination");
                                              passA = res.getString("airportName");
                                              }
          PreparedStatement st2 = conn.prepareStatement("SELECT userCredit FROM user_table WHERE userID=?");
                                        st2.setInt(1, passUID);
                                        res = st2.executeQuery();
                                             while(res.next()){
                                              passCredit = res.getInt("userCredit"); 
                                              }
                                             
                               
        }catch(SQLException ex){
            System.out.println(ex);
        }
          
       
          try {
      FileWriter writer = new FileWriter("C:\\Users\\chris\\Documents\\NetBeansProjects\\IndizaSAServer\\src\\paymentGeneratedFiles\\writeInvoiceFile" + passID + ".txt");
      writer.write(String.format("%s\t\t%s\t%s\t\t%s\t%s\t%s" + String.format("%n"), heading6, heading7, heading8, heading9, heading10, heading11));
      writer.write(String.format("%s\t\t%s\t\t%s\t%s\t%s\t\t%s" + String.format("%n"), passUID, passFID, passA, passD, "R" + passPrice, "R" + passCredit));
      writer.close();
      System.out.println("Data report file written");
          }catch(IOException e){
            System.out.println(e);
        }
        
        try{
          conn = DriverManager.getConnection(url, DBusername, DBpassword);
          PreparedStatement st = conn.prepareStatement("INSERT INTO transactionrecords_table(userID, invoiceFile) VALUES (?,?)");
          st.setInt(1, passUID);                           
          FileReader r = new FileReader("C:\\Users\\chris\\Documents\\NetBeansProjects\\IndizaSAServer\\src\\paymentGeneratedFiles\\writeInvoiceFile" + passID + ".txt");                                
	  st.setCharacterStream(2, r);				
          st.execute();
          System.out.println("Data inserted......");
        }catch(SQLException | FileNotFoundException ex){
            System.out.println(ex);
        }
      
   
    }
}
