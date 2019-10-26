package self.liang.dynamic.proxy.example.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {
    // 需要代理的目标对象
    private Object targetObject;

    public Object newProxy(Object targetObject) {
        // 将目标对象传入进行代理
        this.targetObject = targetObject;
        // 返回代理对象
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }

    // invoke方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 进行逻辑处理的函数
        checkPopedom();
        Object ret = null;
        // 调用invoke方法
        ret = method.invoke(targetObject, args);
        return ret;
    }

    private void checkPopedom() {
        // 模拟检查权限
        System.out.println("检查权限：checkPopedom()!");
    }
}
