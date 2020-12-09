package employee.dao;

import employee.dao.entity.EmployeeEntity;
import employee.exception.EmployeeAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
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
public class EmployeeDaoImpl implements EmployeeDao {

    private final EmployeeRepository employeeRepository;

    @Override
    public void createEmployee(Employee employee) throws EmployeeAlreadyExistsException {

        EmployeeEntity employeeEntity;

        try {
            employeeEntity = queryEmployee(employee.getEmpNo());

        }
        catch (UnknownEmployeeException e){

            employeeEntity = EmployeeEntity.builder()
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
        EmployeeAlreadyExistsException employeeException = new EmployeeAlreadyExistsException("Employee with employee number " + employee.getEmpNo()+" already exists.");
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
    public Employee readByEmpNo(int empNo) throws UnknownEmployeeException {

        EmployeeEntity employeeEntity = queryEmployee(empNo);
        log.trace("Employee found by employee number: {}", empNo);
        return new Employee(employeeEntity.getEmpNo(),employeeEntity.getBirthDate(),employeeEntity.getFirstName(),employeeEntity.getLastName(),
                                employeeEntity.getGender(),employeeEntity.getHireDate());

    }

    @Override
    public void deleteByEmpNo(int empNo) throws UnknownEmployeeException {

        EmployeeEntity employeeEntity = queryEmployee(empNo);
        employeeRepository.deleteByEmpNo(empNo);
        log.trace("Employee deleted: {}", employeeEntity);

    }

    @Override
    public void update(Employee employee) throws UnknownEmployeeException {

        EmployeeEntity employeeEntity = queryEmployee(employee.getEmpNo());

        employeeEntity.setEmpNo(employee.getEmpNo());
        employeeEntity.setBirthDate(employee.getBirthDate());
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setGender(employee.getGender());
        employeeEntity.setHireDate(employee.getHireDate());

        employeeRepository.save(employeeEntity);
        log.trace("Department updated: {}", employeeEntity);
    }

    private EmployeeEntity queryEmployee(int empNo) throws UnknownEmployeeException {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findByEmpNo(empNo);

        if (!employeeEntity.isPresent()) {
            UnknownEmployeeException employeeException = new UnknownEmployeeException("Employee with employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+employeeException.getMessage(),employeeException.getClass());
            throw employeeException;
        }
        return employeeEntity.get();
    }
}
