package server_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client{
    private static DataInputStream fromServer;
    private static DataOutputStream toServer;

    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8000);
            fromServer = new DataInputStream(s.getInputStream());
            toServer = new DataOutputStream(s.getOutputStream());


            int lengthOfArray = fromServer.readInt();
            int[] array = new int[lengthOfArray];
            for (int i = 0; i < lengthOfArray; i++) {
                array[i] = fromServer.readInt();
            }
            int numberOfClients = fromServer.readInt();
            int id = fromServer.readInt();

            int myPart = id + (lengthOfArray/numberOfClients);
            int sum = 0;
            for (int i = id; i < myPart; i++) {
                sum+= array[i];
            }

            toServer.writeInt(sum);
            toServer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}