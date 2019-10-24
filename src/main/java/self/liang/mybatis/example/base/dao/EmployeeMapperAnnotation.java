package self.liang.mybatis.example.base.dao;

import org.apache.ibatis.annotations.Select;
import self.liang.mybatis.example.base.Employee;

public interface EmployeeMapperAnnotation {

    @Select("select * from tbl_employee where id = #{id}")
    public Employee getEmpById(Integer id);
}
