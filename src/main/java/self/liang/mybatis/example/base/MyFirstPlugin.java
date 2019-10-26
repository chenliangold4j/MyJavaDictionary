package self.liang.mybatis.example.base;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Statement;
import java.util.Properties;


@Intercepts({
        @Signature(type = StatementHandler.class,method="parameterize",args = Statement.class)
})
/**
 * 插件：实现interceptor的实现类
 *       使用@intercepts注解完成插件签名
 *       注册到全局配置文件
 *
 *    注意创建代理的时候对象先被1代理，，然后被2代理。。所以先执行2.后执行1
 */
public class MyFirstPlugin implements Interceptor {

    /**
     * 拦截目标方法执行
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //动态改变sql运行的参数。以前1号员工。实际从数据库查询2号
        System.out.println("intercept:"+invocation.getTarget());
        //statementHandler ==》 ParameterHandler==》parameterObject==》
        MetaObject metaObject =  SystemMetaObject.forObject(invocation.getTarget());
        Object value =     metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句的参数： "+value);
        metaObject.setValue("parameterHandler.parameterObject",2);//这里就修改了目标的参数
        Object result =    invocation.proceed();
        return result;
    }

    @Override
    public Object plugin(Object target) {

        System.out.println("plugin---:"+target);
        //我们可以借助Plugin的wrap方法使用当前Interceptor包装我们的对象
        Object wrap =   Plugin.wrap(target,this);
        return wrap;
    }

    /**
     * 将插件注册时的property属性设置进来
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("prperties:"+properties);
    }
}
