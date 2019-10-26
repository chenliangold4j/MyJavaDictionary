package self.liang.dynamic.proxy.example;

import self.liang.dynamic.proxy.example.cglib.CGLibProxy;
import self.liang.dynamic.proxy.example.java.JDKProxy;

public class ProxyTest {
    public static void main(String[] args) {
        IUserManager userManager = (IUserManager) new CGLibProxy().createProxyObject(new UserMananger());
        System.out.println("CGLibProxy：");
        userManager.addUser(112, "root");
        System.out.println("JDKProxy：");

        JDKProxy jdkProxy = new JDKProxy();
        IUserManager userManagerJDK = (IUserManager)jdkProxy.newProxy(new UserMananger());
        userManagerJDK.addUser(112, "root");
    }
}
