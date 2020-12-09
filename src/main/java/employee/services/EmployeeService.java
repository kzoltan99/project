package employee.services;

import employee.exception.EmployeeAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.model.Employee;

import java.util.Collection;

public interface EmployeeService {

    Collection<Employee> getAllEmployee();
    void recordEmployee(Employee employee) throws EmployeeAlreadyExistsException;
    void deleteEmployeeByEmpNo(int empNo) throws UnknownEmployeeException;
    void updateEmployee(Employee employee) throws UnknownEmployeeException;

}
