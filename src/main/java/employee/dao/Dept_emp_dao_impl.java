package employee.dao;

import employee.dao.entity.Department_entity;
import employee.dao.entity.Dept_emp_entity;
import employee.dao.entity.Employee_entity;
import employee.exception.Dept_emp_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.exception.Unknown_dept_emp_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Dept_emp;
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
public class Dept_emp_dao_impl implements Dept_emp_dao {

    private final Dept_emp_repository deptEmpRepository;
    private final Department_repository departmentRepository;
    private final Employee_repository employeeRepository;

    @Override
    public void createDeptEmp(Dept_emp deptEmp) throws Dept_emp_already_exists_exception, Unknown_employee_exception, Unknown_department_exception {

        Dept_emp_entity deptEmpEntity;

        try {
            deptEmpEntity = queryDeptEmp(queryEmployee(deptEmp.getEmpNo()),queryDepartment(deptEmp.getDeptNo()));

        }
        catch (Unknown_dept_emp_exception e){

            deptEmpEntity = Dept_emp_entity.builder()
                    .empNo(queryEmployee(deptEmp.getEmpNo()))
                    .deptNo(queryDepartment(deptEmp.getDeptNo()))
                    .fromDate(deptEmp.getFromDate())
                    .toDate(deptEmp.getToDate())
                    .build();
            log.info("DepartmentEntity: {}", deptEmpEntity);
            deptEmpRepository.save(deptEmpEntity);
            return;
        }
        Dept_emp_already_exists_exception deptEmpException = new Dept_emp_already_exists_exception("DepEmp " + deptEmp.getEmpNo()+" already exists.");
        log.error("Exception: {} thrown with message: "+deptEmpException.getMessage(),deptEmpException.getClass());
        throw deptEmpException;

    }



    @Override
    public Collection<Dept_emp> readAll() {
        return StreamSupport.stream(deptEmpRepository.findAll().spliterator(),false)
                .map(entity -> new Dept_emp(
                        entity.getEmpNo().getEmpNo(),
                        entity.getDeptNo().getDeptNo(),
                        entity.getFromDate(),
                        entity.getToDate()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public Dept_emp readByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_emp_exception {

        Dept_emp_entity deptEmpEntity = queryDeptEmp(empNo,deptNo);
        log.trace("DeptEmp found by employee number: {} and department number: {}",empNo,deptNo);
        return new Dept_emp(deptEmpEntity.getEmpNo().getEmpNo(), deptEmpEntity.getDeptNo().getDeptNo(),
                                                                deptEmpEntity.getFromDate(),deptEmpEntity.getToDate());

    }

    @Override
    public void deleteByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_emp_exception {

        Dept_emp_entity deptEmpEntity = queryDeptEmp(empNo, deptNo);
        deptEmpRepository.deleteByEmpNoAndDeptNo(empNo, deptNo);
        log.trace("DeptEmp deleted: {}", deptEmpEntity);

    }

    @Override
    public void update(Dept_emp deptEmp) throws Unknown_dept_emp_exception, Unknown_employee_exception, Unknown_department_exception {

        Dept_emp_entity deptEmpEntity;

        deptEmpEntity = queryDeptEmp(queryEmployee(deptEmp.getEmpNo()),queryDepartment(deptEmp.getDeptNo()));

        deptEmpEntity.setFromDate(deptEmp.getFromDate());
        deptEmpEntity.setToDate(deptEmp.getToDate());

        deptEmpRepository.save(deptEmpEntity);
        log.trace("DeptEmp updated: {}", deptEmpEntity);
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

    private Dept_emp_entity queryDeptEmp(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_emp_exception {
        Optional<Dept_emp_entity> deptEmpEntity = deptEmpRepository.findByEmpNoAndDeptNo(empNo, deptNo);

        if (!deptEmpEntity.isPresent()) {
            Unknown_dept_emp_exception deptEmpException = new Unknown_dept_emp_exception("DeptEmp with Employee number " + empNo + " and Department number "+ deptNo +" doesn't exist.");
            log.error("Exception: {} thrown with message: "+deptEmpException.getMessage(),deptEmpException.getClass());
            throw deptEmpException;
        }
        return deptEmpEntity.get();
    }
}