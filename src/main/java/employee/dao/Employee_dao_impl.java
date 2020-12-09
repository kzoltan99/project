package employee.dao;

import employee.dao.entity.Employee_entity;
import employee.exception.Employee_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class Employee_dao_impl implements Employee_dao {

    private final Employee_repository employeeRepository;

    @Override
    public void createEmployee(Employee employee) throws Employee_already_exists_exception {

        Employee_entity employeeEntity;

        try {
            employeeEntity = queryEmployee(employee.getEmpNo());

        }
        catch (Unknown_employee_exception e){

            employeeEntity = Employee_entity.builder()
                    .empNo(employee.getEmpNo())
                    .birthDate(employee.getBirthDate())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .gender(employee.getGender())
                    .hireDate(employee.getHireDate())
                    .build();
            log.info("EmployeeEntity: {}", employeeEntity);
            employeeRepository.save(employeeEntity);
            return;
        }
        Employee_already_exists_exception employeeException = new Employee_already_exists_exception("Employee with employee number " + employee.getEmpNo()+" already exists.");
        log.error("Exception: {} thrown with message: "+employeeException.getMessage(),employeeException.getClass());
        throw employeeException;

    }



    @Override
    public Collection<Employee> readAll() {
        return StreamSupport.stream(employeeRepository.findAll().spliterator(),false)
                .map(entity -> new Employee(
                        entity.getEmpNo(),
                        entity.getBirthDate(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getGender(),
                        entity.getHireDate()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public Employee readByEmpNo(int empNo) throws Unknown_employee_exception {

        Employee_entity employeeEntity = queryEmployee(empNo);
        log.trace("Employee found by employee number: {}", empNo);
        return new Employee(employeeEntity.getEmpNo(),employeeEntity.getBirthDate(),employeeEntity.getFirstName(),employeeEntity.getLastName(),
                                employeeEntity.getGender(),employeeEntity.getHireDate());

    }

    @Override
    public void deleteByEmpNo(int empNo) throws Unknown_employee_exception {

        Employee_entity employeeEntity = queryEmployee(empNo);
        employeeRepository.deleteByEmpNo(empNo);
        log.trace("Employee deleted: {}", employeeEntity);

    }

    @Override
    public void update(Employee employee) throws Unknown_employee_exception {

        Employee_entity employeeEntity = queryEmployee(employee.getEmpNo());

        employeeEntity.setEmpNo(employee.getEmpNo());
        employeeEntity.setBirthDate(employee.getBirthDate());
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setGender(employee.getGender());
        employeeEntity.setHireDate(employee.getHireDate());

        employeeRepository.save(employeeEntity);
        log.trace("Department updated: {}", employeeEntity);
    }

    private Employee_entity queryEmployee(int empNo) throws Unknown_employee_exception {
        Optional<Employee_entity> employeeEntity = employeeRepository.findByEmpNo(empNo);

        if (!employeeEntity.isPresent()) {
            Unknown_employee_exception employeeException = new Unknown_employee_exception("Employee with employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+employeeException.getMessage(),employeeException.getClass());
            throw employeeException;
        }
        return employeeEntity.get();
    }
}
