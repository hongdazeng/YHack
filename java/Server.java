import java.io.*;
import java.lang.Exception.*;
import java.net.*;
import java.util.ArrayList;

public class Server implements Runnable {
    Socket socket;
    ServerSocket serverSocket;
    ArrayList<User> users;
    
    public Server(int port) throws SocketException, IOException {
        //FIX ME
        if (0 <= port && port <= 65535) {
            serverSocket = new ServerSocket(port);
            return;
        }
        System.out.println("The given port is not in bounds.\n"
                               + "Port must be between 0 and 65535");
    }
    
    public Server(Socket socket) {
        //FIX ME
        this.socket = socket;
    }
    
    public Server() throws SocketException, IOException {
        serverSocket = new ServerSocket(/*What is a cool number?*/5280);
    }
    
    public void run() {
        try {
            System.out.println("We should be on port: " + this.getLocalPort());
            while(true) {
                Socket client = serverSocket.accept();
                PrintWriter rea = new PrintWriter(client.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                
            break;
            }
        } catch (IOException e) {
            System.out.println("Something is wrong.\n");
        }
    }
    
    public int getLocalPort()  {
            String port = ("" + serverSocket);
            int returned = port.indexOf("t=");
            String portnum = port.substring(returned + 2, port.length() - 1);
            return Integer.parseInt(portnum);
        }
                 
}
