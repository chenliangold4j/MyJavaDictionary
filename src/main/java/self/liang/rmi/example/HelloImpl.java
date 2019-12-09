package self.liang.rmi.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 必须继承UnicastRemoteObject（继承RemoteServer->继承RemoteObject->实现Remote，Serializable），
 * 只有继承UnicastRemoteObject类，才表明其可以作为远程对象，被注册到注册表中供客户端远程调用
 */
public class HelloImpl extends UnicastRemoteObject implements IHello {

    protected HelloImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHelloToSomeBody(String someBodyName) throws RemoteException {
        System.out.println("Connected sucessfully!");
        return "你好，" + someBodyName + "!";
    }
}
