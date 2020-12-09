package employee.services;

import employee.dao.Salary_dao;
import employee.dao.entity.Employee_entity;
import employee.exception.Salary_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.exception.Unknown_salary_exception;
import employee.model.Salary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class Salary_service_impl implements Salary_service {

    private final Salary_dao salaryDao;

    @Override
    public Collection<Salary> getAllSalary() {
        return salaryDao.readAll();
    }

    @Override
    public void recordSalary(Salary salary) throws Salary_already_exists_exception, Unknown_employee_exception {
        salaryDao.createSalary(salary);
    }

    @Transactional
    public void deleteByEmpNoAndFromDate(Employee_entity empNo, Date fromDate) throws Unknown_salary_exception, Unknown_employee_exception {
        salaryDao.deleteByEmpNoAndFromDate(empNo, fromDate);
    }

    @Override
    public void updateSalary(Salary salary) throws Unknown_salary_exception, Unknown_employee_exception {
        salaryDao.update(salary);
    }
}
