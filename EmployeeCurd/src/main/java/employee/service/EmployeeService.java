package employee.service;

import employee.pojo.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> handleGetEmps();

    int handleAddEmp(Employee employee);

    boolean handleCheckEmpnameUsed(String empname);

    Employee handleGetEmp(Integer id);

    int handleUpdateEmp(Employee employee);

    int handleDeleteEmp(Integer id);

    int handleDeleteEmps(List<Integer> ids);

}
