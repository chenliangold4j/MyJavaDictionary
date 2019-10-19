package self.liang.mybatis.example.base;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import self.liang.mybatis.example.base.dao.EmployeeMapper;
import self.liang.mybatis.example.base.dao.EmployeeMapperAnnotation;

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
        test1(sqlSession);
        test2(sqlSession);
        test3(sqlSession);
        sqlSession.close();

    }



    private static void  test1( SqlSession sqlSession){
        //直接通过语句id进行调用，公司用的就是这种再加上封装
        Employee employee =  sqlSession.selectOne("self.liang.mybatis.example.base.dao.EmployeeMapper.getEmpById",1);
        System.out.println(employee);
    }

    private static void  test2( SqlSession sqlSession){
        //通过mapper进行查询。mmall用的就是这种。由生成器生成的
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

    private static void  test3( SqlSession sqlSession){
        //通过注解类来查询
        EmployeeMapperAnnotation employeeMapper =  sqlSession.getMapper(EmployeeMapperAnnotation.class);
        Employee employee =  employeeMapper.getEmpById(1);
        System.out.println(employee);
    }


}
