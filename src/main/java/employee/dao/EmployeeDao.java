package employee.dao;

import employee.exception.EmployeeAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.model.Employee;

import java.util.Collection;

public interface EmployeeDao {

    Collection<Employee> readAll();
    Employee readByEmpNo(int empNo) throws UnknownEmployeeException;
    void createEmployee(Employee employee) throws EmployeeAlreadyExistsException;
    void deleteByEmpNo(int empNo) throws UnknownEmployeeException;
    void update(Employee employee) throws UnknownEmployeeException;

}
