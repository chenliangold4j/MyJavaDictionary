package self.liang.mybatis.example.base;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import self.liang.mybatis.example.base.dao.EmployeeMapper;

import java.io.IOException;
import java.io.InputStream;

public class CreateSqlSessionByXml {

    public static void main(String[] args) throws IOException {

        SqlSessionFactory sqlSessionFactory = getFactory();
        //2。 获取sqlSession实例，能够执行已经映射的语句

        /*
            1 sqlSession代表一次会话，用完需要关闭
            2 sqlSession和sqlSession是非线程安全的。每次时候都应该去获取新的对象。
            3 两个重要配置文件
                mybatis全局配置。包括数据库连接池，事务管理信息等
                sql映射文件：保存了每一个sql语句的映射信息
         */

        SqlSession sqlSession = sqlSessionFactory.openSession();
//        test1(sqlSession);
        test2(sqlSession);
        sqlSession.close();

    }

    private static void  test1( SqlSession sqlSession){
        //直接通过语句id进行调用
        Employee employee =  sqlSession.selectOne("self.liang.mybatis.example.base.getEmpById",1);
        System.out.println(employee);
    }

    private static void  test2( SqlSession sqlSession){
        EmployeeMapper employeeMapper =  sqlSession.getMapper(EmployeeMapper.class);
         Employee employee =  employeeMapper.getEmpById(1);
        System.out.println(employee);
    }

    private static SqlSessionFactory getFactory() throws IOException {
        //全局配置文件
        String resource = "mybatisConfig/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

}
