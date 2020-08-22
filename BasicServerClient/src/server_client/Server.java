package server_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static int numberOfClients = 0;
    private static ArrayList<Socket> clients = new ArrayList<>();
    private static int totalSum = 0;

    public static void main(String[] args){
        int[] arr = {1,2,3,4,5,6,7,8,9,10};//just example

        try {
            ServerSocket serverSocket = new ServerSocket(8500);
            while (true) {
                Socket s = serverSocket.accept();
                numberOfClients++;
                clients.add(s);
                for (int j = 0; j < clients.size(); j++) {
                    Socket tempSocket = clients.get(j);
                    DataOutputStream outputToClient = new DataOutputStream(tempSocket.getOutputStream());
                    outputToClient.writeInt(arr.length);
                    for (int i = 0; i < arr.length; i++) {
                        outputToClient.writeInt(arr[i]);
                    }
                    outputToClient.writeInt(numberOfClients);//n
                    outputToClient.writeInt(numberOfClients-1);//id

                    DataInputStream inputFromClient = new DataInputStream(tempSocket.getInputStream());
                    double partialSum = inputFromClient.readDouble();
                    totalSum += partialSum;
                }
                System.out.println("Sum achieved: " + totalSum + '\n');
            }
        }
        catch(IOException ex) {
            System.err.println(ex);
        }
        System.out.println("Total sum:" + totalSum);
    }
}

