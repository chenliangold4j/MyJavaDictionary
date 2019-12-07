package self.liang.socket.example.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClient {

    public static void main(String[] args)throws  Exception {

        InetAddress inetAddress  = InetAddress.getByName("224.5.6.7");
        MulticastSocket multicastSocket = new MulticastSocket(8888);
        multicastSocket.joinGroup(inetAddress);

        byte[] buf = new byte[256];
        while (true){
            DatagramPacket datagramPacket = new DatagramPacket(buf,buf.length);
            multicastSocket.receive(datagramPacket);

            System.out.println("接受到"+new String(datagramPacket.getData()));
        }

    }
}
