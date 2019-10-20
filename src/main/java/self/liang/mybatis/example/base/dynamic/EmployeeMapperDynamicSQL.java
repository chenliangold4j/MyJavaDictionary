package self.liang.mybatis.example.base.dynamic;

import self.liang.mybatis.example.base.Employee;

import java.util.List;

public interface EmployeeMapperDynamicSQL {
    List<Employee>  getEmpsByConditionIf(Employee employee);
    List<Employee>  getEmpsByConditionTrim(Employee employee);

    List<Employee>  getEmpsByConditionChoose(Employee employee);

    public Integer  updateEmp(Employee employee);

    List<Employee> getEmpsByConditionForeach(List<Integer> ids);
}
