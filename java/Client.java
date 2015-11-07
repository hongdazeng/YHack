import java.io.*; 
import java.net.*; 

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        Socket socket = new Socket("127.0.0.1", /*same cool number*/5280);
        ObjectOutputStream writingClient = new ObjectOutputStream(socket.getOutputStream());
        writingClient.flush(); 
        ObjectInputStream readingClient = new ObjectInputStream(socket.getInputStream());
        
        writingClient.close();
        readingClient.close();
    }
}