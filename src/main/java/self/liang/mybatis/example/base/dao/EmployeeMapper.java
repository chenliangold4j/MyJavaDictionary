package self.liang.mybatis.example.base.dao;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import self.liang.mybatis.example.base.Employee;

import java.util.List;
import java.util.Map;

/**
 * nameSpace用接口全类名
 * 返回类型全类名
 * 语句id为方法名
 * 就可以直接生成代理使用
 */
public interface EmployeeMapper {


    public Employee getEmpById(Integer id);

    public int addEmp(Employee employee);

    public int update(Employee employee);

    public int delete(Integer id);

    public Employee getEmpByIdAndLastName(Integer id, String lastName);

    List<Employee> getEmpsByLastNameLike(String lastName);

    public Map<String,Object> getEmpMapById(Integer id);

    @MapKey("id")//告诉mybatis封装这个map使用哪个属性作为主键
    public Map<Integer,Employee> findAll();



}
