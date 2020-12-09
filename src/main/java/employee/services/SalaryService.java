package employee.services;

import employee.dao.entity.EmployeeEntity;
import employee.exception.SalaryAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.exception.UnknownSalaryException;
import employee.model.Salary;

import java.sql.Date;
import java.util.Collection;

public interface SalaryService {

    Collection<Salary> getAllSalary();
    void recordSalary(Salary salary) throws SalaryAlreadyExistsException, UnknownEmployeeException;
    void deleteByEmpNoAndFromDate(EmployeeEntity empNo, Date fromDate) throws UnknownSalaryException, UnknownEmployeeException;
    void updateSalary(Salary salary) throws UnknownSalaryException, UnknownEmployeeException;

}
