package clientServerTries;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class ClientServer1 {
    public static void main(String argv[]) throws Exception
    {
        // SERVER
        ServerSocket welcomeSocket = new ServerSocket(556);
        String sentenceToSend;
        String sentenceToReceive;
        Socket clientSocket = new Socket(InetAddress.getByName("172.27.99.78"), 556);

        while(true)
        {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToServer =new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromUser =new BufferedReader(new InputStreamReader(System.in));
            sentenceToReceive = inFromClient.readLine();
            System.out.print("Client2: " + sentenceToReceive);
            System.out.print("Enter sentence to send: ");
            sentenceToSend =inFromUser.readLine();
            //System.out.print("Sentence sent: " + sentenceToSend);
            outToServer.writeBytes(sentenceToSend);
            connectionSocket.close();
        }

    }
}