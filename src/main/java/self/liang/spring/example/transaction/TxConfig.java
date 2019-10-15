package self.liang.spring.example.transaction;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import self.liang.MarkUtil;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 *
 *
 * @Transactional 表示当前方法为事务方法
 * @EnableTransactionManagement 开启注解事务功能
 *  PlatformTransactionManager 配置事务管理器
 *
 *
 *  原理：
 *      1）@EnableTransactionManagement
 *          利用import导入TransactionManagementConfigurationSelector
 *           TransactionManagementConfigurationSelector会注册 AutoProxyRegistrar, ProxyTransactionManagementConfiguration
 *      2）AutoProxyRegistrar
 *            给容器中注册一个InfrastructureAdvisorAutoProxyCreator组件。这是个bean后处理器
 *             InfrastructureAdvisorAutoProxyCreator：？
 *             利用后处理器机制在对象创建之后，包装对象，返回一个代理带i想（增强器),代理对象执行方法拦截器
 *     3）ProxyTransactionManagementConfiguration：是个配置类
 *              给容器中注册事务增强器
 *                  事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource
 *                  事务拦截器，
 *                      TransactionInterceptor 保存了事务属性信息，事务管理器
 *                      他是一个MethodInterceptor，在目标方法执行的时候执行拦截器链
 *                                  invokeWithinTransaction ：先获取事务属性
 *                                                            在获取PlatformTransactionManager 平台事务管理器如果没有指定
 *                                                            则按照类型获取
 *                                                            执行目标方法。异常则用事务管理器回滚。正常则提交事务
 */
@Configuration
@ComponentScan("self.liang.spring.example.transaction")
@EnableTransactionManagement
public class TxConfig {

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("liang");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://192.168.3.4:3306/for_tx");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource){
      MarkUtil.soutWithMark(dataSource);
      return  new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Autowired DataSource dataSource){
        return  new DataSourceTransactionManager(dataSource);
    }

}
