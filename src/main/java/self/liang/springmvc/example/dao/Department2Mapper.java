package self.liang.springmvc.example.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import self.liang.springmvc.example.entity.Department2;
import self.liang.springmvc.example.entity.Department2Example;

public interface Department2Mapper {
    long countByExample(Department2Example example);

    int deleteByExample(Department2Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(Department2 record);

    int insertSelective(Department2 record);

    List<Department2> selectByExample(Department2Example example);

    Department2 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Department2 record, @Param("example") Department2Example example);

    int updateByExample(@Param("record") Department2 record, @Param("example") Department2Example example);

    int updateByPrimaryKeySelective(Department2 record);

    int updateByPrimaryKey(Department2 record);
}