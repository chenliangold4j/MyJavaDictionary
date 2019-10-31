package self.liang.netty.example.com.self.worker;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class WorkerProtocolEncoder extends MessageToByteEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String msg, ByteBuf byteBuf) throws Exception {
        msg = "<data>"+msg+"</data>";
        byte[] bytes = msg.getBytes("UTF-8");
        int length = bytes.length;
        String head=String.format("%010d", length);
        String pack=head+msg;
        byteBuf.writeBytes(pack.getBytes("UTF-8"));
    }
}
