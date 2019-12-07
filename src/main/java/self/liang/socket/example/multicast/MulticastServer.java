package self.liang.socket.example.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 组播的优点：
 1）需要相同数据流的客户端加入相同的组共享一条数据流，节省了服务器的负载。具备广播所具备的优点。
 2）由于组播协议是根据接受者的需要对数据流进行复制转发，
 所以服务端的服务总带宽不受客户接入端带宽的限制。IP协议允许有2亿6千多万个组播，所以其提供的服务可以非常丰富。
 3）此协议和单播协议一样允许在Internet宽带网上传输。

 组播的缺点：
 1）与单播协议相比没有纠错机制，发生丢包错包后难以弥补，但可以通过一定的容错机制和QOS加以弥补。
 2）现行网络虽然都支持组播的传输，但在客户认证、QOS等方面还需要完善，这些缺点在理论上都有成熟的解决方案，
 只是需要逐步推广应用到现存网络当中。
 */
public class MulticastServer {

    public static void main(String[] args) throws IOException, InterruptedException {

//        IP网络的组播一般通过组播IP地址来实现。组播IP地址就是D类IP地址，即224.0.0.0至239.255.255.255之间的IP地址。
        InetAddress inetAddress  = InetAddress.getByName("224.5.6.7");

        MulticastSocket multicastSocket = new MulticastSocket();
//        multicastSocket.joinGroup(inetAddress);
        for(int i = 0;i<10;i++){
            String data = "hello world";
            multicastSocket.send(new DatagramPacket(data.getBytes(),data.getBytes().length,inetAddress,8888));
            TimeUnit.SECONDS.sleep(2);
        }

    }
}
