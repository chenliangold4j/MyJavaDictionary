package self.liang.mybatis.example.base;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

public class CreateSqlSessionInCode {
    public static void main(String[] args) {
        DataSource dataSource = getPooledDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
//        configuration("/mybatisConfig/mapper/employeeMapper.xml",Employee.class);//
        //config错误暂时不指定要怎么办  TODO

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);


        //2。 获取sqlSession实例，能够执行已经映射的语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // * @param statement Unique identifier matching the statement to use.
        // * @param parameter A parameter object to pass to the statement.
        Employee employee =  sqlSession.selectOne("self.liang.mybatis.example.base.selectEmployee",1);
        sqlSession.close();

    }

    static DataSource getBlogDataSource(){

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("liang");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://192.168.3.4:3306/for_tx");
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    static DataSource getPooledDataSource(){
        PooledDataSource pooledDataSource = new PooledDataSource();
        pooledDataSource.setUsername("liang");
        pooledDataSource.setPassword("123456");
        pooledDataSource.setUrl("jdbc:mysql://192.168.3.4:3306/for_tx");
        pooledDataSource.setDriver("com.mysql.jdbc.Driver");
        return pooledDataSource;
    }
}
