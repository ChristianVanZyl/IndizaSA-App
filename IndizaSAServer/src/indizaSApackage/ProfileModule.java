package indizaSApackage;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileModule {
    
    private final String url = "jdbc:mysql://localhost:3306/indizadb";
    private final String DBusername = "root";
    private final String DBpassword = "root";
    private String email, password;
    private int userBookID;
    Reservation resv = new Reservation();
    List<String> reservationList = new ArrayList<>();
    private String reservationStringModel;
    Connection conn;
    User user = new User();
    Gson gson = new Gson();
    private String statusGet;
    List<String> userList = new ArrayList<>();
    private String userStringModel;
    private String statusActive = "active";
    private String cancellable = "Cancelled";
    public ProfileModule() {
    }
    
    public void fetchUserData(){
    
                        try{  
                            
                            List<String> listOfMessages = (List<String>)Datahandler.getObjectInputStream().readObject();        
                               
                                email = listOfMessages.get(0); 
                                password = listOfMessages.get(1);
                            
                             conn = DriverManager.getConnection(url, DBusername, DBpassword);
                                     // check whether user has enough credit in account
                          
             
                             PreparedStatement st = conn.prepareStatement("SELECT * FROM user_table WHERE userEmail =? AND userPassword =?");
                             st.setString(1, email);
                             st.setString(2, password);
                             ResultSet rs = st.executeQuery();
                                          while(rs.next()){
                                              user.setUserID(rs.getInt("userID"));
                                              user.setUserName(rs.getString("userName"));
                                              user.setUserSurname(rs.getString("userSurname"));
                                              user.setUserEmail(rs.getString("userEmail"));
                                              user.setUserPassword(rs.getString("userPassword"));
                                              user.setUserCredit(rs.getInt("userCredit"));
                                          }   
                                          
                                          userStringModel = gson.toJson(user);
                                          userList.add(userStringModel);
                                          Datahandler.getObjectOutputStream().writeObject(userList); 
                            }catch(SQLException | IOException | ClassNotFoundException ex){
                             System.out.println(ex);
                             }
                     
}
    
    public void fetchBookedFlights(){
    
                          try{  
                            
                             userBookID = user.getUserID();
                             conn = DriverManager.getConnection(url, DBusername, DBpassword);
                                     // check whether user has enough credit in account
                          
             
                             PreparedStatement st = conn.prepareStatement("SELECT f.flightID, f.destination, f.dateOf, r.reservationID, r.statusR FROM reservation_table AS r JOIN flight_table AS f ON r.flightID = f.flightID WHERE userID =? AND r.statusR = 'active'");
                             st.setInt(1, userBookID);
                         
                             ResultSet rs = st.executeQuery();
                                          while(rs.next()){
                                              
                                              resv.setFlightID(rs.getInt("flightID"));
                                              resv.setDestination(rs.getString("destination"));
                                              resv.setDate(rs.getDate("dateOf").toString());
                                              resv.setReservationID(rs.getInt("reservationID"));
                                              reservationStringModel  = gson.toJson(resv);
                                            
                                              reservationList.add(reservationStringModel);   
                                       
                    
                                             }
                                        
                                          Datahandler.getObjectOutputStream().writeObject(reservationList); 
                            }catch(SQLException | IOException ex){
                             System.out.println(ex);
                             }
       
       
    }
}
