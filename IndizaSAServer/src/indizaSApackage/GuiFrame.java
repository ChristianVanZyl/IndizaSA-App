package indizaSApackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public final class GuiFrame extends JFrame implements ActionListener {

    private JFrame userData;
    private JTable userDataT;
    private JFrame flightData;
    private JTable flightDataT;
    private JFrame userFData;
    private JTable userFT;
    private JLabel heading1;
    private JLabel heading2;
    private JPanel panel1;
    private JPanel panel2;
    private JTextField tbox;
    private JTextField tbox2;
    private Container main;
    private JLabel headingMain;
    private JButton showUsers;
    private JButton showFlights;
    private JButton showUsersForFlight;
    private JButton generatereport;
    private JButton showinvoice;
    private final String url = "jdbc:mysql://localhost:3306/indizadb";
    private final String DBusername = "root";
    private final String DBpassword = "root";
    Connection conn;
    Server serv = new Server();
    PayManagementReceiptModule receipt = new PayManagementReceiptModule();
    private String flightIDQuery;
    
    public GuiFrame(){
        setResizable(false);
        setBounds(250, 80, 1000, 300);
        setDefaultCloseOperation(GuiFrame.EXIT_ON_CLOSE);
        setTitle("IndizaSA Flight Management and Payment System ");

        main = getContentPane();
        main.setLayout(null);
 
        panel1 = new JPanel();
        panel1.setSize(450, 150);
        panel1.setLocation(20, 100);
        panel1.setBackground(Color.DARK_GRAY);
        
        panel2 = new JPanel();
        panel2.setSize(450, 150);
        panel2.setLocation(510, 100);
        panel2.setBackground(Color.gray);
        
        
        headingMain = new JLabel("IndizaSA Flight Management and Payment Panel");
        headingMain.setFont(new Font("Tahoma", Font.BOLD, 20));
        headingMain.setSize(500, 40);
        headingMain.setLocation(250, 10);
        main.add(headingMain);
        
        heading1 = new JLabel("Flight Reservation Management");
        heading1.setFont(new Font("Tahoma", Font.BOLD, 15));
        heading1.setSize(300, 50);
        heading1.setLocation(130, 50);
        main.add(heading1);
        
        heading2 = new JLabel("Payment Management");
        heading2.setFont(new Font("Tahoma", Font.BOLD, 15));
        heading2.setSize(300, 50);
        heading2.setLocation(650, 50);
        main.add(heading2);
  
        // flight management panel
        
        showFlights = new JButton("Show all flights");
        showFlights.setFont(new Font("Tahoma", Font.BOLD, 14));
        showFlights.setSize(150, 20);
        showFlights.setLocation(170, 110);
        showFlights.addActionListener(this);
        main.add(showFlights);

        // PLEASE NOTE, to account for number of entries, only three flights were fully logged

        // One was logged with the full 40 entries - The fully booked flight's ID is 11
        // One was logged with 5 entries - This flight's ID is 14
        // One was logged with 10 entries - This flight's ID is 18
        // Other flight ID entries will result in an error message, as they have not been logged
       
           
        showUsers = new JButton("Show all users");
        showUsers.setFont(new Font("Tahoma", Font.BOLD, 14));
        showUsers.setSize(150, 20);
        showUsers.setLocation(170, 150);
        showUsers.addActionListener(this);
        main.add(showUsers);
        
        showUsersForFlight = new JButton("Show all users for FlightID:");
        showUsersForFlight.setFont(new Font("Tahoma", Font.BOLD, 14));
        showUsersForFlight.setSize(220, 20);
        showUsersForFlight.setLocation(40, 210);
        showUsersForFlight.addActionListener(this);
        main.add(showUsersForFlight);
   
        // payment management panel 
        generatereport = new JButton("Generate cash disbursements report");
        generatereport.setFont(new Font("Tahoma", Font.BOLD, 14));
        generatereport.setSize(310, 20);
        generatereport.setLocation(580, 110);
        generatereport.addActionListener(this);
        main.add(generatereport);
        
        showinvoice = new JButton("Show all transactions for userID:");
        showinvoice.setFont(new Font("Tahoma", Font.BOLD, 14));
        showinvoice.setSize(310, 20);
        showinvoice.setLocation(580, 150);
        showinvoice.addActionListener(this);
        main.add(showinvoice);
        
        tbox = new JTextField();
        tbox.setBounds(120,30,150,20); 
        tbox.setLocation(300, 210);
        main.add(tbox);
    
        
        tbox2 = new JTextField();
        tbox2.setBounds(120,30,50,20); 
        tbox2.setLocation(710, 180);
        main.add(tbox2);
        
        main.add(panel1);
        main.add(panel2);
        setVisible(true);

    }
    
    
    
    public void showUserData(){
    String userDataCol[] = {"userID", "userName", "userSurname", "userEmail", "userCredit"};
    userData = new JFrame("All users on system");
    userData.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    userData.setLayout(new BorderLayout()); 
    DefaultTableModel userT = new DefaultTableModel();
    userT.setColumnIdentifiers(userDataCol);

    userDataT = new JTable();
    userDataT.setModel(userT); 
    userDataT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    userDataT.setFillsViewportHeight(true);
    JScrollPane scroll = new JScrollPane(userDataT);
    scroll.setHorizontalScrollBarPolicy(
    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setVerticalScrollBarPolicy(
    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
    
    int ID;
    String Name;
    String Surname;
    String Email;
    int credit;
    try{ 
    conn = DriverManager.getConnection(url, DBusername, DBpassword);
    String query = "SELECT userID, userName, userSurname, userEmail, userCredit FROM user_table";                                 
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet rs = st.executeQuery();
    int i =0;
    while(rs.next())
    {
    ID = rs.getInt("userID");
    Name = rs.getString("userName");
    Surname = rs.getString("userSurname");
    Email = rs.getString("userEmail"); 
    credit = rs.getInt("userCredit");
    userT.addRow(new Object[]{ID, Name, Surname, Email, credit});
    i++; 
    }
    if(i <1)
    {
    JOptionPane.showMessageDialog(null, "Something went wrong","Error",
    JOptionPane.ERROR_MESSAGE);
    }}catch(HeadlessException | SQLException ex)
    {
    JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
    JOptionPane.ERROR_MESSAGE);
    }
    userData.add(scroll);
    userData.setVisible(true);
    userData.setSize(500,500);
    
    }
    
 
    public void showusersF(){
    
    String usersOnFlight[] = {"flightID", "flightName", "flightDate", "userID", "userName", "userSurname", "userEmail"};
    userFData = new JFrame("All users booked for specified flight");
    userFData.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    userFData.setLayout(new BorderLayout()); 
    DefaultTableModel flightT = new DefaultTableModel();
    flightT.setColumnIdentifiers(usersOnFlight);
    
    userFT = new JTable();
    userFT.setModel(flightT); 
    userFT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    userFT.setFillsViewportHeight(true);
    JScrollPane scroll = new JScrollPane(userFT);
    scroll.setHorizontalScrollBarPolicy(
    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setVerticalScrollBarPolicy(
    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
    
    String flightIDQuery = tbox.getText();
    int flightID;

    String fname;
    Date fdate;
    int ID;
    String Name;
    String Surname;
    String Email;
   
    try{
    conn = DriverManager.getConnection(url, DBusername, DBpassword);
    String query = "SELECT u.userID, u.userName, u.userSurname, u.userEmail, f.flightID, f.destination, f.dateOf FROM reservation_table AS r JOIN user_table AS u ON r.userID = u.userID JOIN flight_table AS f ON r.flightID = f.flightID WHERE r.statusR = 'active' AND r.flightID = "  + flightIDQuery;                               
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet rs = st.executeQuery();
    int i =0;
    while(rs.next())
    {
    ID = rs.getInt("userID");
    Name = rs.getString("userName");
    Surname = rs.getString("userSurname");
    Email = rs.getString("userEmail");    
    flightID = rs.getInt("flightID");
    fname = rs.getString("destination");
    fdate = rs.getDate("dateOf");  

    flightT.addRow(new Object[]{flightID, fname, fdate, ID, Name, Surname, Email});
    i++; 
    }
    if(i <1)
    {
    JOptionPane.showMessageDialog(null, "Something went wrong","Error",
    JOptionPane.ERROR_MESSAGE);
    }else
    {
     
    }}catch(SQLException ex){
        System.out.println("" + ex);}
    userFData.add(scroll);
    userFData.setVisible(true);
    userFData.setSize(1400,500);
    
    }
    
    
    
    public void showFlightData(){
    String flightDataCol[] = {"flightID", "flightName", "flightPrice", "flightDate", "deptTime", "duration", "arrivalTime", "numSeatsLeft", "airport" };
    flightData = new JFrame("All flights on system");
    flightData.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    flightData.setLayout(new BorderLayout()); 
    DefaultTableModel flightT = new DefaultTableModel();
    flightT.setColumnIdentifiers(flightDataCol);

    flightDataT = new JTable();
    flightDataT.setModel(flightT); 
    flightDataT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    flightDataT.setFillsViewportHeight(true);
    JScrollPane scroll = new JScrollPane(flightDataT);
    scroll.setHorizontalScrollBarPolicy(
    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setVerticalScrollBarPolicy(
    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
    
    int flightID;
    String destination;
    int price;
    Date date;
    String deptTime; 
    String duration;
    String arrivalTime;
 
    int numSeats;
      String airport;
    try{ 
    conn = DriverManager.getConnection(url, DBusername, DBpassword);
    String query = "SELECT flight_table.flightID, flight_table.destination, flight_table.price, flight_table.dateOf, flight_table.deptTime, flight_table.duration, flight_table.arrivalTime, flight_table.numSeatsLeft, airport_table.airportName \n" +
"FROM flight_table, airport_table  WHERE flight_table.airportID = airport_table.airportID ORDER BY flight_table.flightID;";                                 
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet rs = st.executeQuery();
    int i =0;
    while(rs.next())
    {
     
    flightID = rs.getInt("flightID");
    destination = rs.getString("destination");
    price = rs.getInt("price");
    date = rs.getDate("dateOf"); 
    deptTime = rs.getString("deptTime");
    duration = rs.getString("duration");
    arrivalTime = rs.getString("arrivalTime");
    numSeats = rs.getInt("numSeatsLeft");
    airport = rs.getString("airportName");
    flightT.addRow(new Object[]{flightID, destination, price, date, deptTime, duration, arrivalTime, numSeats, airport});
    i++; 
    }
    if(i <1)
    {
    JOptionPane.showMessageDialog(null, "Something went wrong","Error",
    JOptionPane.ERROR_MESSAGE);
    }}catch(HeadlessException | SQLException ex)
    {
    JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
    JOptionPane.ERROR_MESSAGE);
    }
    flightData.add(scroll);
    flightData.setVisible(true);
    flightData.setSize(1200,300);
   
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
               if(e.getSource() == showUsers){
                    showUserData();
        }else if(e.getSource() == showFlights){
            
               showFlightData();
        
        }else if(e.getSource() == showUsersForFlight){
            showusersF();
        }
        else if(e.getSource() == generatereport){
        
                generatereport();
        
        }else if(e.getSource() == showinvoice){
                
            showinvoice();
        
        }
    }
    
    
    public void generatereport(){
    
        receipt.cashDisbursementReport();
 
        JOptionPane.showMessageDialog(null, "Please find the generated report in the folder labelled `paymentGeneratedFiles`");
    }
    
    public void showinvoice(){
        
        flightIDQuery = tbox2.getText();
     
        receipt.transactionReportForUser(flightIDQuery);
        JOptionPane.showMessageDialog(null, "Please find the invoice report in the folder:maindrive/ProgramData/MySQL/MySQLServer8.0/Uploads referenced as writtenOutputFile ending with the given ID");
   
    }
}
