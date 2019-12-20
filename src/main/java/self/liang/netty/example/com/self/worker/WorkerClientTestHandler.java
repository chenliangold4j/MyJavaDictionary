package self.liang.netty.example.com.self.worker;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.dom4j.Element;
import self.liang.cryption.example.AESCoder;
import self.liang.xml.example.Dom4jUtil;
import self.liang.xml.example.XMLCommonBean;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerClientTestHandler extends SimpleChannelInboundHandler<String> {

    private AESCoder aesCoder = new AESCoder();

    private String token = "VD6F-5NDH-MBF6-K";

    String login_id = null;

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
        String content = aesCoder.defaultEncrypt("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
                "<DSXClient>\n" +
                "         <Login>\n" +
                "\t        <token>" + token + "</token>\n" +
                "         </Login>\n" +
                "</DSXClient>\n");
        ctx.write(content);
        ctx.flush();
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

        String value = aesCoder.defaultDecrypt(Dom4jUtil.getRootString(Dom4jUtil.parse(s)));
        System.out.println("-----接到"+value);
        if ( value != null&& value.equals("<ping/>")) {
            String content = aesCoder.defaultEncrypt("<pong/>");
            ctx.write(content);
            ctx.flush();
        } else if(value != null) {
            XMLCommonBean xmlCommonBean = Dom4jUtil.getXmlBeanFromXml(value);
            if (xmlCommonBean.getRoot().equals("DSXClient") && xmlCommonBean.getMain().equals("Login")) {
                String key = xmlCommonBean.getDataMap().get("serverTime").toString();
                key = key.replace(" ", "");
                key = key.replace(":", "");
                key = key.replace("-", "");
                key = key + "--";
                aesCoder.setDefault_mkey(key.getBytes("UTF-8"));
                login_id = xmlCommonBean.getDataMap().get("result").toString();
                String content = aesCoder.defaultEncrypt(getAppLogin());
                ctx.write(content);
                ctx.flush();
            }
            if(xmlCommonBean.getRoot().equals("DSXClient") && xmlCommonBean.getMain().equals("RemoteUnlock")){
//                 <UnlockInfo>
        //            <device_number>1071000029735</device_number>
        //            <hash>J6fPdn3JVojKw0n2nM2mIDrihto=</hash>
        //            <checkTime>2019-06-28 14:43:13</checkTime>
        //            <photo>照片的base64字符串</photo>
        //            <lockSn>HTV8-L400-BI40-0</lockSn>
        //        </UnlockInfo>
                Map<String,Object> ulockInfo = (Map<String, Object>) xmlCommonBean.getDataMap().get("UnlockInfo");
                 String hash =ulockInfo.get("hash").toString();
                String checkTime = ulockInfo.get("checkTime").toString();
                String lockSN = ulockInfo.get("lockSn").toString();
                String appUserId = "12";
                System.out.println(getRemoteUnlock(hash,checkTime,lockSN,appUserId));
                String content = aesCoder.defaultEncrypt(getRemoteUnlock(hash,checkTime,lockSN,appUserId));
                ctx.write(content);
                ctx.flush();
            }
        }
    }

    public String getAppLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("phone_no", "16620095702");
        map.put("password", Base64.getEncoder().encodeToString("123456".getBytes()));
        map.put("login_id", login_id);
        Element element = Dom4jUtil.createResponseXmlMonolayer("AppUserManage", "Login", map);
        return Dom4jUtil.asXML(element);
    }

    public String getRemoteUnlock(String hash,String checkTime,String lockSN,String appUserId ) {
        Map<String, String> map = new HashMap<>();
        map.put("hash", hash);
        map.put("checkTime",checkTime);
        map.put("lockSn", lockSN);
        map.put("app_user_id", appUserId);
        Element element = Dom4jUtil.createResponseXmlMonolayer("AppUserDeviceMananger", "RemoteUnlock", map);
        return Dom4jUtil.asXML(element);
    }

}

