package employee.dao;

import employee.dao.entity.Employee_entity;
import employee.exception.Salary_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.exception.Unknown_salary_exception;
import employee.model.Salary;

import java.sql.Date;
import java.util.Collection;

public interface Salary_dao {

    Collection<Salary> readAll();
    Salary readByEmpNo(Employee_entity empNo) throws Unknown_salary_exception;
    void createSalary(Salary salary) throws Salary_already_exists_exception, Unknown_employee_exception;
    void deleteByEmpNoAndFromDate(Employee_entity empNo, Date fromDate) throws Unknown_salary_exception, Unknown_employee_exception;
    void update(Salary salary) throws Unknown_salary_exception, Unknown_employee_exception;

}
