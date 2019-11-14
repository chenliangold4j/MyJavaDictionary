package self.liang.netty.example.com.self.worker;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class WorkersStressTest {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).
                    handler(new WorkerClientInitializer());
            ChannelFuture channelFuture = null;
            for(int i = 0;i<150;i++) {
//                 Thread.sleep(1000);
                 channelFuture = bootstrap.connect("192.168.0.116", 9999).sync();
            }
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
