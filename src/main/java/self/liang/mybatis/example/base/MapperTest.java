package self.liang.mybatis.example.base;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import self.liang.mybatis.example.base.dao.EmployeeMapper;
import self.liang.mybatis.example.base.dao.EmployeeMapperAnnotation;

import java.io.IOException;
import java.io.InputStream;

public class MapperTest {

    public static volatile Integer id = null;
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

        //这是自动提交的sqlsession
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);

//        add(sqlSession);
        modify(sqlSession);
//        delete(sqlSession);
        sqlSession.commit();
        sqlSession.close();


    }

    private static void  add( SqlSession sqlSession){
        Employee employee = new Employee();
        employee.setLastName("tom");
        employee.setGender("2");
        employee.setEmail("tom@13.com");
        int count =  sqlSession.insert("self.liang.mybatis.example.base.dao.EmployeeMapper.addEmp",employee);
        id = employee.getId();
        System.out.println("insert"+count);
    }

    private static void  modify( SqlSession sqlSession){
        Employee employee = new Employee();
        employee.setLastName("jerry");
        employee.setGender("2");
        employee.setEmail("tom@13.com");
        employee.setId(4);

        EmployeeMapper employeeMapper =  sqlSession.getMapper(EmployeeMapper.class);
        int count =  employeeMapper.update(employee);
        System.out.println("update:"+count);
    }



    private static void  delete( SqlSession sqlSession){
        EmployeeMapper employeeMapper =  sqlSession.getMapper(EmployeeMapper.class);
        int count =  employeeMapper.delete(id);
        System.out.println("delete:"+count);
    }

    private static SqlSessionFactory getFactory() throws IOException {
        //全局配置文件
        String resource = "mybatisConfig/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
}
