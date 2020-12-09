package employee.services;

import employee.dao.SalaryDao;
import employee.dao.entity.EmployeeEntity;
import employee.exception.SalaryAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.exception.UnknownSalaryException;
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
public class SalaryServiceImpl implements SalaryService{

    private final SalaryDao salaryDao;

    @Override
    public Collection<Salary> getAllSalary() {
        return salaryDao.readAll();
    }

    @Override
    public void recordSalary(Salary salary) throws SalaryAlreadyExistsException, UnknownEmployeeException {
        salaryDao.createSalary(salary);
    }

    @Transactional
    public void deleteByEmpNoAndFromDate(EmployeeEntity empNo, Date fromDate) throws UnknownSalaryException, UnknownEmployeeException {
        salaryDao.deleteByEmpNoAndFromDate(empNo, fromDate);
    }

    @Override
    public void updateSalary(Salary salary) throws UnknownSalaryException, UnknownEmployeeException {
        salaryDao.update(salary);
    }
}
