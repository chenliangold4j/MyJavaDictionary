package self.liang.netty.example.com.self.worker;

import io.netty.channel.ChannelHandlerContext;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import self.liang.cryption.example.AESCoder;
import self.liang.xml.example.Dom4jUtil;
import self.liang.xml.example.XMLCommonBean;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public class WorkerRunable implements Runnable {

    private String login_id = null;

    private String token = null;

    private boolean isLogin = false;

    private ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<>();

    private AESCoder aesCoder = new AESCoder();

    ChannelHandlerContext ctx;

    public WorkerRunable(String token, ChannelHandlerContext ctx) {
        this.token = token;
        this.ctx = ctx;
    }

    @Override
    public void run() {
        try {
            String content = aesCoder.defaultEncrypt("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
                    "<DSXClient>\n" +
                    "         <Login>\n" +
                    "\t        <token>" + token + "</token>\n" +
                    "         </Login>\n" +
                    "</DSXClient>\n");
            ctx.write(content);
            ctx.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Thread.sleep(200);
                if (isLogin) {
                    String content = aesCoder.defaultEncrypt(queryAllDevice());
                    ctx.write(content);
                    ctx.flush();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void HandleOther(String s) throws UnsupportedEncodingException, DocumentException {
        String value = aesCoder.defaultDecrypt(Dom4jUtil.getRootString(Dom4jUtil.parse(s)));
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
                isLogin = true;
            }
        }
        ResultCollector.increaseCount();
    }


    public String getUpdateDeviceXml() {
        Map<String, String> map = new HashMap<>();
        map.put("deviceNumber", "LNGN-WHD6-A838-813");
        map.put("name", "test");
        map.put("login_id", login_id);
        map.put("device_type_id", "3");
        map.put("valid_type", "0");
        map.put("remark", "testetstest");
        Element element = Dom4jUtil.createResponseXmlMonolayer("DeviceManager", "Update", map);
        return Dom4jUtil.asXML(element);
    }

    public String getStartHeartbeat() {
        Map<String, String> map = new HashMap<>();
        map.put("deviceNumber", "HTV8-L400-BI40-0");
        map.put("login_id", login_id);
        Element element = Dom4jUtil.createResponseXmlMonolayer("DSXClient", "StartHeartbeat", map);
        return Dom4jUtil.asXML(element);
    }

    public String getAppLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("phone_no", "13538978846");
        map.put("password", Base64.getEncoder().encodeToString("123456".getBytes()));
        map.put("login_id", login_id);
        Element element = Dom4jUtil.createResponseXmlMonolayer("AppUserManage", "Login", map);
        return Dom4jUtil.asXML(element);
    }

    public String getDeviceEvent() {
        Map<String, String> map = new HashMap<>();
        map.put("HeartBeat", "true");
        map.put("IdentifyHistoryUpload", "true");
        map.put("PersonInfoUpload", "true");
        map.put("UploadAlert", "true");
        map.put("app_user_id", "13");
        map.put("login_id", login_id);
        Element element = Dom4jUtil.createResponseXmlMonolayer("AppUserDeviceMananger", "DeviceEvent", map);
        return Dom4jUtil.asXML(element);
    }

    public String getUnlock() {
        Map<String, String> map = new HashMap<>();
        map.put("deviceNumber", "556G-TMOC-UAA6-A");
        map.put("login_id", login_id);
        Element element = Dom4jUtil.createResponseXmlMonolayer("DeviceManager", "Unlock", map);
        return Dom4jUtil.asXML(element);
    }

    public String queryAllDevice() {
        Map<String, String> map = new HashMap<>();
        map.put("login_id", login_id);
        Element element = Dom4jUtil.createResponseXmlMonolayer("DeviceManager", "FindAll", map);
        return Dom4jUtil.asXML(element);
    }

}
