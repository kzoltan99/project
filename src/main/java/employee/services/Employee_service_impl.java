package employee.services;

import employee.dao.Employee_dao;
import employee.exception.Employee_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class Employee_service_impl implements Employee_service {

    private final Employee_dao employeeDao;

    @Override
    public Collection<Employee> getAllEmployee() {
        return employeeDao.readAll();
    }

    @Override
    public void recordEmployee(Employee employee) throws Employee_already_exists_exception {
        employeeDao.createEmployee(employee);
    }

    @Override
    public void deleteEmployeeByEmpNo(int empNo) throws Unknown_employee_exception {
        employeeDao.deleteByEmpNo(empNo);
    }

    @Override
    public void updateEmployee(Employee employee) throws Unknown_employee_exception {
        employeeDao.update(employee);
    }
}