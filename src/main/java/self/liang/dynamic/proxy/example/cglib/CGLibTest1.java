package self.liang.dynamic.proxy.example.cglib;

import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

public class CGLibTest1 {
    public static void main(String[] args) {
        testMain();//主要用这种
//        test1();//拦截所有方法
//        test2();//InvocationHandler示例
//        test3();//拦截指定类型方法
    }


    static void testMain(){
        //MethodInterceptor的方式相对通用。。相当于可以执行bean前后处理
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before method run...");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("after method run...");
                return result;
            }
        });
        SampleClass sample = (SampleClass) enhancer.create();
        sample.test(null);
    }

    static void test1() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "Hello cglib";
            }
        });
        SampleClass proxy = (SampleClass) enhancer.create();
        System.out.println(proxy.test(null)); //拦截test，输出Hello cglib 没有执行原方法
        System.out.println(proxy.toString());
        System.out.println(proxy.getClass());
//        System.out.println(proxy.hashCode());
       /* 上述代码中，FixedValue用来对所有拦截的方法返回相同的值，
       从输出我们可以看出来，Enhancer对非final方法test()、toString()、hashCode()进行了拦截，
       没有对getClass进行拦截。由于hashCode()方法需要返回一个Number，
       但是我们返回的是一个String，这解释了上面的程序中为什么会抛出异常*/
    }

    static void test2() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    System.out.println(method.getName());
//                    method.invoke(proxy,args); 这样会陷入死循环。。。
                    /**
                     * 使用invoke方法来替换直接访问类的方法，
                     * 但是你必须注意死循环。
                     * 因为invoke中调用的任何原代理类方法，
                     * 均会重新代理到invoke方法中
                     */
                    return "hello cglib";
                } else {
                    System.out.println(method.getName());
                    throw new RuntimeException("Do not know what to do");
                }
            }
        });
        SampleClass proxy = (SampleClass) enhancer.create();
        System.out.println(proxy.test(null));
        System.out.println(proxy.toString());
    }

    static void test3() {
        Enhancer enhancer = new Enhancer();
        CallbackHelper callbackHelper = new CallbackHelper(SampleClass.class, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            return "Hello cglib";
                        }
                    };
                } else {
                    return NoOp.INSTANCE;
                }
            }
        };
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        SampleClass proxy = (SampleClass) enhancer.create();
        System.out.println(proxy.test(null));
        System.out.println(proxy.toString());
        System.out.println(proxy.hashCode());
        //这个也没有执行原方法。。拦截当前类。且返回值是string的方法
    }



}
