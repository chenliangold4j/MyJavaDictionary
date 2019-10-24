package self.liang.springmvc.example.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import self.liang.springmvc.example.entity.EmployeeG2;
import self.liang.springmvc.example.entity.EmployeeG2Example;

public interface EmployeeG2Mapper {
    long countByExample(EmployeeG2Example example);

    int deleteByExample(EmployeeG2Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(EmployeeG2 record);

    int insertSelective(EmployeeG2 record);

    List<EmployeeG2> selectByExample(EmployeeG2Example example);

    EmployeeG2 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EmployeeG2 record, @Param("example") EmployeeG2Example example);

    int updateByExample(@Param("record") EmployeeG2 record, @Param("example") EmployeeG2Example example);

    int updateByPrimaryKeySelective(EmployeeG2 record);

    int updateByPrimaryKey(EmployeeG2 record);
}