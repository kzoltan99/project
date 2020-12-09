package employee.dao;

import employee.dao.entity.EmployeeEntity;
import employee.exception.SalaryAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.exception.UnknownSalaryException;
import employee.model.Salary;

import java.sql.Date;
import java.util.Collection;

public interface SalaryDao {

    Collection<Salary> readAll();
    Salary readByEmpNo(EmployeeEntity empNo) throws UnknownSalaryException;
    void createSalary(Salary salary) throws SalaryAlreadyExistsException, UnknownEmployeeException;
    void deleteByEmpNoAndFromDate(EmployeeEntity empNo, Date fromDate) throws UnknownSalaryException, UnknownEmployeeException;
    void update(Salary salary) throws UnknownSalaryException, UnknownEmployeeException;

}
