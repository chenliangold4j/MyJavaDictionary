package self.liang.mybatis.example.base.dynamic;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import self.liang.mybatis.example.base.Department;
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
//        getEmpsByConditionForeach(sqlSession);
        addEmps(sqlSession);
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

    public static void addEmps( SqlSession sqlSession ){
        EmployeeMapperDynamicSQL employeeMapperDynamicSQL =  sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
        employee.setLastName("user1");
        employee.setGender("1");
        employee.setEmail("user1@132.com");
        employee.setDepartment(new Department(1));
        Employee employee1 = new Employee();
        employee1.setLastName("user2");
        employee1.setGender("1");
        employee1.setEmail("user2@132.com");
        employee1.setDepartment(new Department(1));
        Employee employee2 = new Employee();
        employee2.setLastName("user3");
        employee2.setGender("1");
        employee2.setEmail("user3@132.com");
        employee2.setDepartment(new Department(1));

        List<Employee> list = new ArrayList<>();
        list.add(employee);
        list.add(employee1);
        list.add(employee2);

        employeeMapperDynamicSQL.addEmps(list);

    }


    private static SqlSessionFactory getFactory() throws IOException {
        //全局配置文件
        String resource = "mybatisConfig/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

}
