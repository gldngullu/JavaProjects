package sendReceiveObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class StudentServer {
    private static int x = 0;
    public static void main(String[] args) {
        new StudentServer();
    }

    public StudentServer() {
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server started at " + new Date() + '\n');
            // Listen for a connection request
            Socket socket = serverSocket.accept();
            // Create data input and output streams
            ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                Student s = (Student) inputFromClient.readObject();
                s.setAddedByServer(x++);
                outputToClient.writeObject(s);
            }
        }
        catch(Exception ex) {
            System.out.println("exception oldu");
        }
    }

}
