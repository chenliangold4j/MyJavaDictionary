package self.liang.spring.example.profile;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * profile  动态激活和奇幻你系统组件的功能
 *
 * 开发环境，测试环境，生成环境
 * 数据源（A）（B）（C）
 *
 */
@Profile("test")//如果是test 这个配置类才起效果
@Configuration
@PropertySource("classpath:/cpdbconfig.properties")
public class MyConfigOfProfile implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String username;

    private StringValueResolver resolver;

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}") String pwd){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://192.168.3.4:3306/formydic");
        try {
            dataSource.setDriverClass(resolver.resolveStringValue("${db.driverClass}"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://192.168.3.4:3306/formydic2");
        dataSource.setDriverClass("com.mysql.jdb.Driver");
        return dataSource;
    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${db.password}") String pwd){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://192.168.3.4:3306/formydic3");
        try {
            dataSource.setDriverClass(resolver.resolveStringValue("${db.driverClass}"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.resolver = resolver;
    }
}

