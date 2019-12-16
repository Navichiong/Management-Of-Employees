package employee.pojo;

import javax.validation.constraints.Pattern;

public class Department {
    private Integer depId;

    @Pattern(regexp = "^[\\u2E80-\\u9FFF]{1,10}$",message = "部门名称只能为1~10位的中文")
    private String deptName;

    public Department() {
    }

    public Department(Integer depId, String deptName) {
        this.depId = depId;
        this.deptName = deptName;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    @Override
    public String toString() {
        return "Department{" +
                "depId=" + depId +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}