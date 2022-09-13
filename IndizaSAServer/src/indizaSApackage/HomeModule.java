package indizaSApackage;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class HomeModule {
    
    private final String url = "jdbc:mysql://localhost:3306/indizadb";
    private final String DBusername = "root";
    private final String DBpassword = "root";
    Connection conn;
    

    public HomeModule() {
    }
    
    public void homepageOperations(){
    
                                try{
   
                                        Flight flightModel = new Flight();
                                        Gson gson = new Gson();
                                        String flightStringModel;

                                        List<String> flightList = new ArrayList<>();
                                        conn = DriverManager.getConnection(url, DBusername, DBpassword);
                                      
                                       String query = "SELECT flight_table.flightID, flight_table.destination, flight_table.price, flight_table.dateOf, flight_table.deptTime, "
                                        + "flight_table.duration, " + "flight_table.arrivalTime, flight_table.numSeatsLeft, airport_table.airportName \n" +
                                        "FROM flight_table, airport_table  WHERE flight_table.airportID = airport_table.airportID ORDER BY flight_table.flightID;";                                 
                                        PreparedStatement st = conn.prepareStatement(query);
                                        ResultSet rs = st.executeQuery();
                                       
                                            while(rs.next())
                                            {
     
                                            flightModel.setFlightID(rs.getInt("flightID"));
                                            flightModel.setDestination(rs.getString("destination"));
                                            flightModel.setPrice(rs.getInt("price"));
                                            flightModel.setDateOf(rs.getDate("dateOf").toString()); 
                                            flightModel.setDeptTime(rs.getString("deptTime"));
                                            flightModel.setDuration(rs.getString("duration"));
                                            flightModel.setArrivalTime(rs.getString("arrivalTime")); 
                                            flightModel.setNumSeatsLeft(rs.getInt("numSeatsLeft"));
                                            flightModel.setAirport(rs.getString("airportName")); 
                                            flightStringModel = gson.toJson(flightModel);
                                            flightList.add(flightStringModel);
                                            }
                                            Datahandler.getObjectOutputStream().writeObject(flightList); 
    
                            }catch(SQLException | IOException ex){
                             System.out.println(ex);
                             }
}
}
