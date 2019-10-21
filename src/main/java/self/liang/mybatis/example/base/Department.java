package self.liang.mybatis.example.base;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable {

    static final long serialVersionUID = 2222L;

    private Integer id;
    private String departmentName;
    private List<Employee> employees;

    public Department() {
    }


    public Department(Integer id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", employees=" + employees +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
