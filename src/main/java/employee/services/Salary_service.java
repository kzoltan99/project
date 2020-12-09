package employee.services;

import employee.dao.entity.Employee_entity;
import employee.exception.Salary_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.exception.Unknown_salary_exception;
import employee.model.Salary;

import java.sql.Date;
import java.util.Collection;

public interface Salary_service {

    Collection<Salary> getAllSalary();
    void recordSalary(Salary salary) throws Salary_already_exists_exception, Unknown_employee_exception;
    void deleteByEmpNoAndFromDate(Employee_entity empNo, Date fromDate) throws Unknown_salary_exception, Unknown_employee_exception;
    void updateSalary(Salary salary) throws Unknown_salary_exception, Unknown_employee_exception;

}
