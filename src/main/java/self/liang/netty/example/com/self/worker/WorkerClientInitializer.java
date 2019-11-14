package self.liang.netty.example.com.self.worker;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.nio.charset.Charset;

public class WorkerClientInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new WokerProtocolDecoder());
        pipeline.addLast(new WorkerProtocolEncoder());
        pipeline.addLast(new WorkerHandler());
    }
}
