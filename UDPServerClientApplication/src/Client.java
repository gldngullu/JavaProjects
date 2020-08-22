import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

class Client {

    public static void main(String args[]) throws Exception {

        DatagramSocket clientSocket = new DatagramSocket(3000);

        InetAddress IPAddress = InetAddress.getByName("localhost");

        byte[] sendData;
        byte[] receiveData = new byte[1024];

        int integer = (int) (Math.random() * 100 + 1);
        System.out.println("Randomized integer: " + integer);

        sendData = ByteBuffer.allocate(4).putInt(integer).array();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8000);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        ByteBuffer wrapped = ByteBuffer.wrap(receivePacket.getData());

        int num = wrapped.getInt();
        System.out.println("Sum received from server: " + num + " - Number sent to server: " + integer);

        clientSocket.close();

    }
}
