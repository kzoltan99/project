package employee.services;

import employee.exception.Employee_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Employee;

import java.util.Collection;

public interface Employee_service {

    Collection<Employee> getAllEmployee();
    void recordEmployee(Employee employee) throws Employee_already_exists_exception;
    void deleteEmployeeByEmpNo(int empNo) throws Unknown_employee_exception;
    void updateEmployee(Employee employee) throws Unknown_employee_exception;

}
