package self.liang.netty.example.com.self.worker;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.dom4j.Element;
import self.liang.cryption.example.AESUtil;
import self.liang.xml.example.Dom4jUtil;
import self.liang.xml.example.XMLCommonBean;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerHandler extends SimpleChannelInboundHandler<String> {

    ExecutorService executors = Executors.newCachedThreadPool();
    ConcurrentHashMap<ChannelHandlerContext,WorkerRunable> cacheMap = new ConcurrentHashMap<>();



    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        WorkerRunable workerRunable = new WorkerRunable(TokenUtil.getValue(),ctx);
        cacheMap.put(ctx,workerRunable);
        executors.execute(workerRunable);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        WorkerRunable  workerRunable =  cacheMap.get(ctx);
        if(workerRunable != null){
            workerRunable.HandleOther(s);
        }
    }

}
