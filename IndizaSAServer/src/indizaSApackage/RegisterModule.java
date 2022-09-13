package indizaSApackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class RegisterModule {
        
    private final String url = "jdbc:mysql://localhost:3306/indizadb";
    private final String DBusername = "root";
    private final String DBpassword = "root";
    Connection conn;
    
    private String regName;
    private String regSurname;
    private String regEmail;
    private String regPassword;
    private int regCredit;
   String registrationMsg = "";
    
    public RegisterModule() {
    }
    
    public void registerProcess(){
    
                                try{
                                        conn = DriverManager.getConnection(url, DBusername, DBpassword);
                                        List<String> regList = (List<String>)Datahandler.getObjectInputStream().readObject();
                                         regName = regList.get(0);
                                         System.out.println(regName);
                                         regSurname = regList.get(1);
                                         System.out.println(regSurname);
                                         regEmail = regList.get(2);
                                         System.out.println(regEmail);
                                         regPassword = regList.get(3);
                                         System.out.println(regPassword);
                                         regCredit = 0;
                                  
                                         List<String> registrationList = new ArrayList<>();   
                                       
                                         PreparedStatement st = conn.prepareStatement("INSERT INTO user_table SET userName=?, userSurname=?, userEmail=?, userPassword=?, userCredit=?");
                                        
                                              st.setString(1, regName);
                                              st.setString(2, regSurname);
                                              st.setString(3, regEmail);
                                              st.setString(4, regPassword);
                                              st.setInt(5, regCredit);
					if (st.executeUpdate() > 0) {
						   registrationMsg = "success";
                                                  
					} else {
						registrationMsg = "failure";
					}
                            
                                        System.out.println(registrationMsg);
                                        registrationList.add(registrationMsg);
                                        Datahandler.getObjectOutputStream().writeObject(registrationList); 
                                          
    
                                    }catch(SQLException | IOException | ClassNotFoundException ex){
                             System.out.println(ex);
                             }
    }                         
}

