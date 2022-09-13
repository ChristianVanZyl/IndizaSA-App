package indizaSApackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Datahandler {

    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null ;

    public Datahandler() {

    }

    public static void setDataHandlers() throws IOException {

        try {

            objectOutputStream = new ObjectOutputStream(Sockethandler.getSocket().getOutputStream());
            objectInputStream = new ObjectInputStream(Sockethandler.getSocket().getInputStream());
        } catch (IOException ex) {
            System.out.println("" + ex);

        }
    }

    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public static ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }
}