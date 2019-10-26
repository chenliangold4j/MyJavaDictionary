package self.liang.mybatis.example.base;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;
@Intercepts({
        @Signature(type = StatementHandler.class,method="parameterize",args = Statement.class)
})
public class MySecondPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MySecondPlugin--:"+invocation.getMethod());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("MySecondPlugin--:"+target);
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
