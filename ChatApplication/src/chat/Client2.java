package chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client2 {

    public static void main(String argv[]) throws Exception
    {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser =new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket(InetAddress.getByName("172.27.99.78"), 556);
        DataOutputStream outToServer =new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        while(true){
            System.out.print("Enter characters to be capitalized: ");
            sentence =inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            Scanner input=new Scanner(System.in);
        }
        //System.out.print("Do you want to enter again? Press '0' for 'yes' and '1' if 'No'.");
        //i=input.nextInt();

    }
}
