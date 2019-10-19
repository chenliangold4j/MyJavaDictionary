package self.liang.mybatis.example.base;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import self.liang.mybatis.example.base.dao.DepartmentMapper;
import self.liang.mybatis.example.base.dao.EmployeeMapper;
import self.liang.mybatis.example.base.dao.EmployeeMapperPlus;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class MapperTest2 {

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

//        SqlSession sqlSession = sqlSessionFactory.openSession();

        //这是自动提交的sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

//        getEmpById(sqlSession);
//        getEmpAndDept(sqlSession);
//        getDeptById(sqlSession);
//        getEmpByIdStep(sqlSession);//两次子查询。。查出这个人的部门。。部门里面还有人员
//        getDeptByIdPlus(sqlSession);
//        getDeptByIdStep(sqlSession);
        sqlSession.close();


    }
    private static void getEmpById(SqlSession sqlSession){
        EmployeeMapperPlus employeeMapper =  sqlSession.getMapper(EmployeeMapperPlus.class);
        Employee employee =  employeeMapper.getEmpById(1);
        System.out.println("getEmpsByLastNameLike:"+employee.getEmail());
    }

    private static void getEmpAndDept(SqlSession sqlSession){
        //表连接查询
        EmployeeMapperPlus employeeMapper =  sqlSession.getMapper(EmployeeMapperPlus.class);
        Employee employee =  employeeMapper.getEmpAndDept(1);
        System.out.println("getEmpsByLastNameLike:"+employee);
    }

    private static void getEmpByIdStep(SqlSession sqlSession){
        //分布查询
        EmployeeMapperPlus employeeMapper =  sqlSession.getMapper(EmployeeMapperPlus.class);
        Employee employee =  employeeMapper.getEmpByIdStep(1);
        System.out.println("getEmpByIdStep:"+employee);
    }

    private static void getDeptById(SqlSession sqlSession){
        DepartmentMapper departmentMapper =  sqlSession.getMapper(DepartmentMapper.class);
        Department department =  departmentMapper.getDeptById(1);
        System.out.println("getEmpAndDept:"+department);
    }

     private static void getDeptByIdStep(SqlSession sqlSession){
        DepartmentMapper departmentMapper =  sqlSession.getMapper(DepartmentMapper.class);
        Department department =  departmentMapper.getDeptByIdStep(1);
        System.out.println("getDeptByIdStep:"+department);
    }

    private static void getDeptByIdPlus(SqlSession sqlSession){
        DepartmentMapper departmentMapper =  sqlSession.getMapper(DepartmentMapper.class);
        Department department =  departmentMapper.getDeptByIdPlus(1);
        System.out.println("getEmpAndDept:"+department);
    }



    private static SqlSessionFactory getFactory() throws IOException {
        //全局配置文件
        String resource = "mybatisConfig/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }



}
