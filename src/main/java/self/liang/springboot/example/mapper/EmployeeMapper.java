package self.liang.springboot.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import self.liang.springboot.example.entity.Employee;

//指定mapper
//@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    public Employee getById(long id);

}
