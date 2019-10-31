package self.liang.netty.example.com.self.worker;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class WokerProtocolDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        byte[] bytes = new byte[10];
        in.readBytes(bytes);
        int bodyLength = Integer.parseInt(new String(bytes,"UTF-8"));
        byte[] bodyBytes = new byte[bodyLength];
        in.readBytes(bodyBytes);
        String body = new String(bodyBytes, "UTF-8");
        list.add(body); // 解析出一条消息
    }
}
