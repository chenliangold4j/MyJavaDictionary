package self.liang.mybatis.example.base.dao;

import self.liang.mybatis.example.base.Department;

public interface DepartmentMapper {
   public   Department getDeptById(Integer id);

   //同时差所有部门的员工
   public Department getDeptByIdPlus(Integer id);

   public Department getDeptByIdStep(Integer id);
}
