package employee.dao;

import employee.dao.entity.Employee_entity;
import employee.dao.entity.Salary_entity;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class Salary_dao_impl implements Salary_dao {

    private final Salary_repository salaryRepository;
    private final Employee_repository employeeRepository;

    @Override
    public void createSalary(Salary salary) throws Salary_already_exists_exception, Unknown_employee_exception {

        Salary_entity salaryEntity;

        try {
            salaryEntity = querySalary(queryEmployee(salary.getEmpNo()));

        }
        catch (Unknown_salary_exception e){

            salaryEntity = Salary_entity.builder()
                    .empNo(queryEmployee(salary.getEmpNo()))
                    .salary(salary.getSalary())
                    .fromDate(salary.getFromDate())
                    .toDate(salary.getToDate())
                    .build();
            log.info("SalaryEntity: {}", salaryEntity);
            salaryRepository.save(salaryEntity);
            return;
        }
        Salary_already_exists_exception SalaryException = new Salary_already_exists_exception("Salary with Employee number " + salary.getEmpNo()+" already exists.");
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
    public Salary readByEmpNo(Employee_entity empNo) throws Unknown_salary_exception {

        Salary_entity salaryEntity = querySalary(empNo);
        log.trace("Salary found by Employee number: {} Salary: {}", empNo,salaryEntity);
        return new Salary(salaryEntity.getEmpNo().getEmpNo(),salaryEntity.getSalary(),salaryEntity.getFromDate(),salaryEntity.getToDate());

    }

    @Transactional
    public void deleteByEmpNoAndFromDate(Employee_entity empNo, Date fromDate) throws Unknown_salary_exception, Unknown_employee_exception {

        Salary_entity salaryEntity = querySalary(queryEmployee(empNo.getEmpNo()));
        salaryRepository.deleteByEmpNoAndFromDate(queryEmployee(empNo.getEmpNo()), fromDate);
        log.trace("Salary deleted: {}", salaryEntity);

    }

    @Override
    public void update(Salary salary) throws Unknown_salary_exception, Unknown_employee_exception {

        Salary_entity salaryEntity = querySalary(queryEmployee(salary.getEmpNo()));

        salaryEntity.setEmpNo(queryEmployee(salary.getEmpNo()));
        salaryEntity.setSalary(salary.getSalary());
        salaryEntity.setFromDate(salary.getFromDate());
        salaryEntity.setToDate(salary.getToDate());

        salaryRepository.save(salaryEntity);
        log.trace("Salary updated: {}", salaryEntity);
    }

    private Salary_entity querySalary(Employee_entity empNo) throws Unknown_salary_exception {
        Optional<Salary_entity> salaryEntity = salaryRepository.findByEmpNo(empNo);

        if (!salaryEntity.isPresent()) {
            Unknown_salary_exception salaryException = new Unknown_salary_exception("Salary with Employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+salaryException.getMessage(),salaryException.getClass());
            throw salaryException;
        }
        return salaryEntity.get();
    }

    private Employee_entity queryEmployee(int empNo) throws Unknown_employee_exception {
        Optional<Employee_entity> employeeEntity = employeeRepository.findById(empNo);

        if (!employeeEntity.isPresent()) {
            Unknown_employee_exception employeeException = new Unknown_employee_exception("Employee with employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+employeeException.getMessage(),employeeException.getClass());
            throw employeeException;
        }
        return employeeEntity.get();
    }
}