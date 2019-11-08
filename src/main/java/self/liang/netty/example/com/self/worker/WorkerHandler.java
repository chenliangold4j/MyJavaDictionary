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

public class WorkerHandler extends SimpleChannelInboundHandler<String> {

    private String login_id = null;

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
        String content =  AESUtil.defaultEncrypt("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
                "<DSXClient>\n" +
                "         <Login>\n" +
                "\t        <token>LVV8-L400-BI40-0</token>\n" +
                "         </Login>\n" +
                "</DSXClient>\n");
        ctx.write(content);
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
        String value = AESUtil.defaultDecrypt(Dom4jUtil.getRootString(Dom4jUtil.parse(s)));
        System.out.println("接到的消息:"+value);
        if(value.equals("<ping/>")){
            String content = AESUtil.defaultEncrypt("<pong/>");
            channelHandlerContext.write(content);
            channelHandlerContext.flush();
        }else{
            XMLCommonBean xmlCommonBean = Dom4jUtil.getXmlBeanFromXml(value);
            if(xmlCommonBean.getRoot().equals("DSXClient") && xmlCommonBean.getMain().equals("Login")){
                String key = xmlCommonBean.getDataMap().get("serverTime").toString();
                key = key.replace(" ","");
                key = key.replace(":","");
                key = key.replace("-","");
                key = key+"--";
                System.out.println("key:"+key);
                AESUtil.setDefault_mkey(key.getBytes("UTF-8"));
                login_id = xmlCommonBean.getDataMap().get("result").toString();
                //测试消息
                String data = getAppLogin();
                System.out.println("发送的数据："+data);
                String content = AESUtil.defaultEncrypt(data);
                channelHandlerContext.write(content);
                channelHandlerContext.flush();
            }else if(xmlCommonBean.getMain().equals("Login") && xmlCommonBean.getRoot().equals("AppUserManage")){
                String data = getUnlock();
                System.out.println("发送的数据："+data);
                String content = AESUtil.defaultEncrypt(data);
                channelHandlerContext.write(content);
                channelHandlerContext.flush();
            }
        }
    }


    public String getUpdateDeviceXml(){
        Map<String,String> map = new HashMap<>();
        map.put("deviceNumber","HTV8-L400-BI40-0");
        map.put("login_id",login_id);
        map.put("device_type_id","3");
        map.put("valid_type","0");
        map.put("remark","testetstest");
        Element element =  Dom4jUtil.createResponseXmlMonolayer("DeviceManager","Update",map);
        return  Dom4jUtil.asXML(element);
    }
    public String getStartHeartbeat(){
        Map<String,String> map = new HashMap<>();
        map.put("deviceNumber","HTV8-L400-BI40-0");
        map.put("login_id",login_id);
        Element element =  Dom4jUtil.createResponseXmlMonolayer("DSXClient","StartHeartbeat",map);
        return  Dom4jUtil.asXML(element);
    }


    public String getAppLogin(){
        Map<String,String> map = new HashMap<>();
        map.put("phone_no","13538978846");
        map.put("password",Base64.getEncoder().encodeToString("123456".getBytes()));
        map.put("login_id",login_id);
        Element element =  Dom4jUtil.createResponseXmlMonolayer("AppUserManage","Login",map);
        return  Dom4jUtil.asXML(element);
    }

    public String getDeviceEvent(){
        Map<String,String> map = new HashMap<>();
        map.put("HeartBeat","true");
        map.put("IdentifyHistoryUpload","true");
        map.put("PersonInfoUpload","true");
        map.put("UploadAlert","true");
        map.put("app_user_id","13");
        map.put("login_id",login_id);
        Element element =  Dom4jUtil.createResponseXmlMonolayer("AppUserDeviceMananger","DeviceEvent",map);
        return  Dom4jUtil.asXML(element);
    }

    public String getUnlock(){
        Map<String,String> map = new HashMap<>();
        map.put("deviceNumber","556G-TMOC-UAA6-A");
        map.put("login_id",login_id);
        Element element =  Dom4jUtil.createResponseXmlMonolayer("DeviceManager","Unlock",map);
        return  Dom4jUtil.asXML(element);
    }

}
