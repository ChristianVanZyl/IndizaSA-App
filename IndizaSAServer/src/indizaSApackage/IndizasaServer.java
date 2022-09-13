package indizaSApackage;

// Programmer: Christian van Zyl
// Last modified: 2021/11/09
// Program name: Indiza Server
public class IndizasaServer {

    
    public static void main(String[] args) {
        
        GuiFrame g = new GuiFrame();
        Server serv = new Server();
      
        serv.runServer();

    }
    
}
