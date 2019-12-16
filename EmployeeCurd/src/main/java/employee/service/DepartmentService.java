package employee.service;

import employee.pojo.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> handleGetDepts();

    long handleGetCountByDeptName(String deptName);

    Integer handleAddDepartment(Department department);

    Department handleGetDeptById(Integer deptId);

    int handleUpdateDept(Department department);

    int deleteDeptById(Integer deptId);

    int deleteMultiDept(List<Integer> deptIds);
}
