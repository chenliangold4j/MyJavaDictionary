package self.liang.mybatis.example.base.dynamic;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import self.liang.mybatis.example.base.Employee;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DynamicSqlTest {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory sqlSessionFactory = getFactory();

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

//        getEmpsByConditionIf(sqlSession);
//        getEmpsByConditionTrim(sqlSession);
//        getEmpsByConditionChoose(sqlSession);
//        updateEmp(sqlSession);
        getEmpsByConditionForeach(sqlSession);
        sqlSession.close();
    }


    public static void getEmpsByConditionIf( SqlSession sqlSession ){
        EmployeeMapperDynamicSQL employeeMapperDynamicSQL =  sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
//        employee.setId(3);
        employee.setLastName("%to%");
        employee.setGender("1");
        System.out.println( employeeMapperDynamicSQL.getEmpsByConditionIf(employee));
    }

    public static void getEmpsByConditionForeach( SqlSession sqlSession ){
        EmployeeMapperDynamicSQL employeeMapperDynamicSQL =  sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(3);
        List<Employee> result = employeeMapperDynamicSQL.getEmpsByConditionForeach(ids);
        System.out.println(result);
    }


    public static void getEmpsByConditionTrim( SqlSession sqlSession ){
        EmployeeMapperDynamicSQL employeeMapperDynamicSQL =  sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
        employee.setLastName("%to%");
        employee.setGender("1");
        System.out.println( employeeMapperDynamicSQL.getEmpsByConditionTrim(employee));
    }
    public static void updateEmp( SqlSession sqlSession ){
        EmployeeMapperDynamicSQL employeeMapperDynamicSQL =  sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
        employee.setId(1);
        employee.setLastName("地方");
        employee.setGender("1");
        System.out.println( employeeMapperDynamicSQL.updateEmp(employee));
    }

    public static void getEmpsByConditionChoose( SqlSession sqlSession ){
        EmployeeMapperDynamicSQL employeeMapperDynamicSQL =  sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
//        employee.setId(3);
        employee.setLastName("%to%");
        employee.setGender("1");
        System.out.println( employeeMapperDynamicSQL.getEmpsByConditionChoose(employee));
    }

    private static SqlSessionFactory getFactory() throws IOException {
        //全局配置文件
        String resource = "mybatisConfig/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

}
