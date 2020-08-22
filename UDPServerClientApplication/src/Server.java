import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class Server {

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(8000);

            int sum = 0;
            byte[] receiveData = new byte[1024];
            byte[] sendData;

            while (true) {
                System.out.println("Server is running...");
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                serverSocket.receive(receivePacket);
                ByteBuffer wrapped = ByteBuffer.wrap(receivePacket.getData());
                int integer = wrapped.getInt();

                InetAddress IpAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                sum = sum + integer;

                sendData = ByteBuffer.allocate(4).putInt(sum).array();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpAddress, port);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
