package employee.pojo;

import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.Pattern;

public class Employee {
    private Integer id;

    @Pattern(regexp = "(^[a-zA-Z0-9_-]{3,16}$)|(^[\\u2E80-\\u9FFF]{2,5})"
    ,message = "员工姓名只能为3到16位的英文字母、数字及下划线的组合或是2到5位的中文")
    private String empname;

    @Email(message = "邮箱格式不正确")
    private String email;

    private Integer gender;

    private Integer dId;

    private Department department;

    public Department getDepartment() {
        return department;
    }

    public Employee() {
    }

    public Employee(Integer id, String empname, String email, Integer gender, Integer dId) {
        this.id = id;
        this.empname = empname;
        this.email = email;
        this.gender = gender;
        this.dId = dId;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname == null ? null : empname.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", empname='" + empname + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", dId=" + dId +
                ", department=" + department +
                '}';
    }
}