package employee.dao;

import employee.dao.entity.Department_entity;
import employee.dao.entity.Dept_manager_entity;
import employee.dao.entity.Employee_entity;
import employee.exception.Dept_manager_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.exception.Unknown_dept_manager_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Dept_manager;
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
public class Dept_manager_dao_impl implements Dept_manager_dao {

    private final Dept_manager_repository deptManagerRepository;
    private final Department_repository departmentRepository;
    private final Employee_repository employeeRepository;

    @Override
    public void createDeptManager(Dept_manager deptManager) throws Dept_manager_already_exists_exception, Unknown_employee_exception, Unknown_department_exception {

        Dept_manager_entity deptManagerEntity;

        try {
            deptManagerEntity = queryDeptManager(queryEmployee(deptManager.getEmpNo()),queryDepartment(deptManager.getDeptNo()));

        }
        catch (Unknown_dept_manager_exception e){

            deptManagerEntity = Dept_manager_entity.builder()
                    .empNo(queryEmployee(deptManager.getEmpNo()))
                    .deptNo(queryDepartment(deptManager.getDeptNo()))
                    .fromDate(deptManager.getFromDate())
                    .toDate(deptManager.getToDate())
                    .build();
            log.info("DeptManagerEntity: {}", deptManagerEntity);
            deptManagerRepository.save(deptManagerEntity);
            return;
        }
        Dept_manager_already_exists_exception deptManagerException = new Dept_manager_already_exists_exception("DeptManager " + deptManager.getEmpNo()+" already exists.");
        log.error("Exception: {} thrown with message: "+deptManagerException.getMessage(),deptManagerException.getClass());
        throw deptManagerException;

    }



    @Override
    public Collection<Dept_manager> readAll() {
        return StreamSupport.stream(deptManagerRepository.findAll().spliterator(),false)
                .map(entity -> new Dept_manager(
                        entity.getEmpNo().getEmpNo(),
                        entity.getDeptNo().getDeptNo(),
                        entity.getFromDate(),
                        entity.getToDate()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public Dept_manager readByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_manager_exception {

        Dept_manager_entity deptManagerEntity = queryDeptManager(empNo,deptNo);
        log.trace("DeptManager found by employee number: {} and department number: {}",empNo,deptNo);
        return new Dept_manager(deptManagerEntity.getEmpNo().getEmpNo(), deptManagerEntity.getDeptNo().getDeptNo(),
                deptManagerEntity.getFromDate(),deptManagerEntity.getToDate());

    }

    @Override
    public void deleteByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_manager_exception {

        Dept_manager_entity deptManagerEntity = queryDeptManager(empNo, deptNo);
        deptManagerRepository.deleteByEmpNoAndDeptNo(empNo, deptNo);
        log.trace("DeptManager deleted: {}", deptManagerEntity);

    }

    @Override
    public void update(Dept_manager deptManager) throws Unknown_dept_manager_exception, Unknown_employee_exception, Unknown_department_exception {

        Dept_manager_entity deptManagerEntity;

        deptManagerEntity = queryDeptManager(queryEmployee(deptManager.getEmpNo()),queryDepartment(deptManager.getDeptNo()));

        deptManagerEntity.setFromDate(deptManager.getFromDate());
        deptManagerEntity.setToDate(deptManager.getToDate());

        deptManagerRepository.save(deptManagerEntity);
        log.trace("DeptManager updated: {}", deptManagerEntity);
    }

    private Department_entity queryDepartment(String deptNo) throws Unknown_department_exception {
        Optional<Department_entity> departmentEntity = departmentRepository.findById(deptNo);

        if (!departmentEntity.isPresent()) {
            Unknown_department_exception departmentException = new Unknown_department_exception("Department with Department number " + deptNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+departmentException.getMessage(),departmentException.getClass());
            throw departmentException;
        }
        return departmentEntity.get();
    }

    private Employee_entity queryEmployee(int empNo) throws Unknown_employee_exception {
        Optional<Employee_entity> employeeEntity = employeeRepository.findById(empNo);

        if (!employeeEntity.isPresent()) {

            Unknown_employee_exception employeeException = new Unknown_employee_exception("Employee with Employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+employeeException.getMessage(),employeeException.getClass());
            throw employeeException;
        }
        return employeeEntity.get();
    }

    private Dept_manager_entity queryDeptManager(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_manager_exception {
        Optional<Dept_manager_entity> deptManagerEntity = deptManagerRepository.findByEmpNoAndDeptNo(empNo, deptNo);

        if (!deptManagerEntity.isPresent()) {
            Unknown_dept_manager_exception deptManagerException = new Unknown_dept_manager_exception("DeptManager with Employee number " + empNo + " and Department number "+ deptNo +" doesn't exist.");
            log.error("Exception: {} thrown with message: "+deptManagerException.getMessage(),deptManagerException.getClass());
            throw deptManagerException;
        }
        return deptManagerEntity.get();
    }
}