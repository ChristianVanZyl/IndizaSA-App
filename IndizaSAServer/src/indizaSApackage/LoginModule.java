package indizaSApackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class LoginModule {

    private String email, password, verification; 
    private final String url = "jdbc:mysql://localhost:3306/indizadb";
    private final String DBusername = "root";
    private final String DBpassword = "root";
    private int userID;
    private String uID;
    Connection conn;
   
    
    public LoginModule() {
       
    }

                                
    public void runLoginMod(){
    
              
    try{
     conn = DriverManager.getConnection(url, DBusername, DBpassword);
                 // data being received by server
                List<String> authList = (List<String>)Datahandler.getObjectInputStream().readObject();
                                   
                                    email = authList.get(0);
                                    System.out.println("User email is " + email);        
                                    password = authList.get(1);
                                    System.out.println("User password is " + password);
                                    
                            
                                    // authenticate details by connecting to db and querying
                                    PreparedStatement st = conn.prepareStatement("SELECT userEmail, userPassword, userID FROM user_table WHERE userEmail =? AND userPassword =?");
                                    st.setString(1, email);
                                    st.setString(2, password);
                                    ResultSet rs = st.executeQuery();
                                          if (rs.next()) {
                                                     verification = "approved";
                                                     userID = rs.getInt("userID");
                                          }     
                                          else{
                                                    verification = "denied";
                                                }
                                          uID = Integer.toString(userID);
                                          
                                         
                                          
                                            // send verification result
                                            System.out.println("Server sending verification result: " + verification);
                                            // server streams for sending    
                                            
                                            // data being sent to client
                                            List<String> verificationList = new ArrayList<>();   
                                            verificationList.add(verification);
                                            verificationList.add(uID);
                                            // data written
                                            Datahandler.getObjectOutputStream().writeObject(verificationList);  
                             }catch(SQLException | IOException | ClassNotFoundException ex){
                             System.out.println(ex);
                             }
                                     
    }
      
}
