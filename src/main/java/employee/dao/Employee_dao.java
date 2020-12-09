package employee.dao;

import employee.exception.Employee_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Employee;

import java.util.Collection;

public interface Employee_dao {

    Collection<Employee> readAll();
    Employee readByEmpNo(int empNo) throws Unknown_employee_exception;
    void createEmployee(Employee employee) throws Employee_already_exists_exception;
    void deleteByEmpNo(int empNo) throws Unknown_employee_exception;
    void update(Employee employee) throws Unknown_employee_exception;

}
