package self.liang.netty.example.com.self.worker;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import self.liang.cryption.example.AESUtil;
import self.liang.xml.example.Dom4jUtil;

public class WorkerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("channelUnregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channelActive");
        ctx.write(AESUtil.defaultEncrypt("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
                "<DSXClient>\n" +
                "         <Login>\n" +
                "\t        <token>LVV8-L400-BI40-0</token>\n" +
                "         </Login>\n" +
                "</DSXClient>\n"));
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("channelInactive");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("--------"+s);
        String value = AESUtil.defaultDecrypt(Dom4jUtil.getRootString(Dom4jUtil.parse(s)));
        if(value.equals("<ping/>")){
            channelHandlerContext.write("<pong/>");
            channelHandlerContext.flush();
        }
        channelHandlerContext.write(AESUtil.defaultEncrypt("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
                "<DSXClient>\n" +
                "         <Login>\n" +
                "\t        <token>LVV8-L400-BI40-0</token>\n" +
                "         </Login>\n" +
                "</DSXClient>\n"));
        channelHandlerContext.flush();
    }
}
