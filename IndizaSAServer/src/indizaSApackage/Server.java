package indizaSApackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Server {
 
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public Server() {
        
    }
    
    public void runServer(){
      
        serverSocket = null;
    
        try {
         
            // server is listening on port 8000
            serverSocket = new ServerSocket(8000);
            System.out.println("Listening to connection requests");
            System.out.println("\n");
  
              
             // running infinite loop for serving new sockets to new clients
            while(true){
                // connecting client to server
                clientSocket = serverSocket.accept();
                Sockethandler.setSocket(clientSocket);
                Datahandler.setDataHandlers();    
                // create a new thread object
                // This thread will handle the client operations separately
                Thread thread = new ClientHandler();
                thread.start();
            }
        }
        catch (IOException e) {
        }
        finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                }
                catch (IOException e) {
                }
            }
        }
    } 
    
    public void stopServer() throws IOException{
        
         serverSocket.close();
         System.out.println("server connection severed");
    }
    
        
     // ClientHandler class
    public class ClientHandler extends Thread {
 
        private String message = "";      
        private final String startingMessage = "start";  
        // modules performing clienthandler operations
        LoginModule log = new LoginModule();
        RegisterModule reg = new RegisterModule();
        HomeModule home = new HomeModule();
        BookingModule book = new BookingModule();
        ProfileModule profile = new ProfileModule();
        
        // Constructor
        public ClientHandler()
        {
 
        }
  
        @Override
        public void run()
        {
                    try {    
                        // infinite while loop receiving messages from client to update state
                        // sending data to client based on state        
                        while(startingMessage.equalsIgnoreCase("start") && Datahandler.getObjectOutputStream()!= null && Datahandler.getObjectInputStream() != null){
                            
                            // if not yet logged in or registered, directed to this if statement first
                            System.out.println("Instructions being received");  
                            System.out.println("\n");
                            // data being received by server
                                List<String> listOfMessages = (List<String>)Datahandler.getObjectInputStream().readObject();        
                                for(int i = 0; i < listOfMessages.size(); i++){ 
                                System.out.println("" + listOfMessages.get(i));
                                }
                                message = listOfMessages.get(0); 
            
                                
                                // depending on message received from client, one of the following statements will be fulfilled and trigger the correct module
                                 if(message.equalsIgnoreCase("Login")){   
                                    
                                    // login verification called
                                     log.runLoginMod();
                                     System.out.println("\n");
                                 
                                    }else if(message.equalsIgnoreCase("Register")){
                                        
                                      // registration operation called
                                      reg.registerProcess();
                                      System.out.println("\n");
                                      
                                    }else if(message.equalsIgnoreCase("Home")){
                                        
                                       // home operations called
                                       home.homepageOperations();
                                       System.out.println("\n");
                                       
                                    }else if(message.equalsIgnoreCase("Book")){
                                    
                                        // booking operation called
                                        book.bookingOperation();
                                      
                                      
                                    }else if(message.equalsIgnoreCase("Profile")){
                                    
                                        // profile operation called
                                        profile.fetchUserData();
                                        profile.fetchBookedFlights();
                                        System.out.println("\n");
                                        
                                    }else if(message.equalsIgnoreCase("CancelBooking")){
                                        // reservation cancelation called
                       
                                        book.cancelReservation();               
                                    }
                                    else{
                                    System.out.println("empty");
                                    } 
                                }} catch(IOException | ClassNotFoundException e){
                                             System.out.println(e);
                                          } 
        }
    }
}
   
                                       
                        
                  
                  
              
                
		


    
 
       
 
           
	
        
                          
  
  
  
 
        
     
        
        

        
        
