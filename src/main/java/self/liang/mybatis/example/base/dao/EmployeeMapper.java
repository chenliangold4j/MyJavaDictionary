package self.liang.mybatis.example.base.dao;

import self.liang.mybatis.example.base.Employee;

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

}
