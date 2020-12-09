package employee.dao;

import employee.dao.entity.EmployeeEntity;
import employee.dao.entity.SalaryEntity;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalaryDaoImpl implements SalaryDao {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void createSalary(Salary salary) throws SalaryAlreadyExistsException, UnknownEmployeeException {

        SalaryEntity salaryEntity;

        try {
            salaryEntity = querySalary(queryEmployee(salary.getEmpNo()));

        }
        catch (UnknownSalaryException e){

            salaryEntity = SalaryEntity.builder()
                    .empNo(queryEmployee(salary.getEmpNo()))
                    .salary(salary.getSalary())
                    .fromDate(salary.getFromDate())
                    .toDate(salary.getToDate())
                    .build();
            log.info("SalaryEntity: {}", salaryEntity);
            salaryRepository.save(salaryEntity);
            return;
        }
        SalaryAlreadyExistsException SalaryException = new SalaryAlreadyExistsException("Salary with Employee number " + salary.getEmpNo()+" already exists.");
        log.error("Exception: {} thrown with message: "+SalaryException.getMessage(),SalaryException.getClass());
        throw SalaryException;

    }




    @Override
    public Collection<Salary> readAll() {
        return StreamSupport.stream(salaryRepository.findAll().spliterator(),false)
                .map(entity -> new Salary(
                        entity.getEmpNo().getEmpNo(),
                        entity.getSalary(),
                        entity.getFromDate(),
                        entity.getToDate()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public Salary readByEmpNo(EmployeeEntity empNo) throws UnknownSalaryException {

        SalaryEntity salaryEntity = querySalary(empNo);
        log.trace("Salary found by Employee number: {} Salary: {}", empNo,salaryEntity);
        return new Salary(salaryEntity.getEmpNo().getEmpNo(),salaryEntity.getSalary(),salaryEntity.getFromDate(),salaryEntity.getToDate());

    }

    @Transactional
    public void deleteByEmpNoAndFromDate(EmployeeEntity empNo, Date fromDate) throws UnknownSalaryException, UnknownEmployeeException {

        SalaryEntity salaryEntity = querySalary(queryEmployee(empNo.getEmpNo()));
        salaryRepository.deleteByEmpNoAndFromDate(queryEmployee(empNo.getEmpNo()), fromDate);
        log.trace("Salary deleted: {}", salaryEntity);

    }

    @Override
    public void update(Salary salary) throws UnknownSalaryException, UnknownEmployeeException {

        SalaryEntity salaryEntity = querySalary(queryEmployee(salary.getEmpNo()));

        salaryEntity.setEmpNo(queryEmployee(salary.getEmpNo()));
        salaryEntity.setSalary(salary.getSalary());
        salaryEntity.setFromDate(salary.getFromDate());
        salaryEntity.setToDate(salary.getToDate());

        salaryRepository.save(salaryEntity);
        log.trace("Salary updated: {}", salaryEntity);
    }

    private SalaryEntity querySalary(EmployeeEntity empNo) throws UnknownSalaryException {
        Optional<SalaryEntity> salaryEntity = salaryRepository.findByEmpNo(empNo);

        if (!salaryEntity.isPresent()) {
            UnknownSalaryException salaryException = new UnknownSalaryException("Salary with Employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+salaryException.getMessage(),salaryException.getClass());
            throw salaryException;
        }
        return salaryEntity.get();
    }

    private EmployeeEntity queryEmployee(int empNo) throws UnknownEmployeeException {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(empNo);

        if (!employeeEntity.isPresent()) {
            UnknownEmployeeException employeeException = new UnknownEmployeeException("Employee with employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+employeeException.getMessage(),employeeException.getClass());
            throw employeeException;
        }
        return employeeEntity.get();
    }
}