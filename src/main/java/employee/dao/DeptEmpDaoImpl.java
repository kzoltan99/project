package employee.dao;

import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.DeptEmpEntity;
import employee.dao.entity.EmployeeEntity;
import employee.exception.DeptEmpAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.exception.UnknownDeptEmpException;
import employee.exception.UnknownEmployeeException;
import employee.model.DeptEmp;
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
public class DeptEmpDaoImpl implements DeptEmpDao {

    private final DeptEmpRepository deptEmpRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void createDeptEmp(DeptEmp deptEmp) throws DeptEmpAlreadyExistsException, UnknownEmployeeException, UnknownDepartmentException {

        DeptEmpEntity deptEmpEntity;

        try {
            deptEmpEntity = queryDeptEmp(queryEmployee(deptEmp.getEmpNo()),queryDepartment(deptEmp.getDeptNo()));

        }
        catch (UnknownDeptEmpException e){

            deptEmpEntity = DeptEmpEntity.builder()
                    .empNo(queryEmployee(deptEmp.getEmpNo()))
                    .deptNo(queryDepartment(deptEmp.getDeptNo()))
                    .fromDate(deptEmp.getFromDate())
                    .toDate(deptEmp.getToDate())
                    .build();
            log.info("DepartmentEntity: {}", deptEmpEntity);
            deptEmpRepository.save(deptEmpEntity);
            return;
        }
        DeptEmpAlreadyExistsException deptEmpException = new DeptEmpAlreadyExistsException("DepEmp " + deptEmp.getEmpNo()+" already exists.");
        log.error("Exception: {} thrown with message: "+deptEmpException.getMessage(),deptEmpException.getClass());
        throw deptEmpException;

    }



    @Override
    public Collection<DeptEmp> readAll() {
        return StreamSupport.stream(deptEmpRepository.findAll().spliterator(),false)
                .map(entity -> new DeptEmp(
                        entity.getEmpNo().getEmpNo(),
                        entity.getDeptNo().getDeptNo(),
                        entity.getFromDate(),
                        entity.getToDate()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public DeptEmp readByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptEmpException {

        DeptEmpEntity deptEmpEntity = queryDeptEmp(empNo,deptNo);
        log.trace("DeptEmp found by employee number: {} and department number: {}",empNo,deptNo);
        return new DeptEmp(deptEmpEntity.getEmpNo().getEmpNo(), deptEmpEntity.getDeptNo().getDeptNo(),
                                                                deptEmpEntity.getFromDate(),deptEmpEntity.getToDate());

    }

    @Override
    public void deleteByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptEmpException {

        DeptEmpEntity deptEmpEntity = queryDeptEmp(empNo, deptNo);
        deptEmpRepository.deleteByEmpNoAndDeptNo(empNo, deptNo);
        log.trace("DeptEmp deleted: {}", deptEmpEntity);

    }

    @Override
    public void update(DeptEmp deptEmp) throws UnknownDeptEmpException, UnknownEmployeeException,UnknownDepartmentException {

        DeptEmpEntity deptEmpEntity;

        deptEmpEntity = queryDeptEmp(queryEmployee(deptEmp.getEmpNo()),queryDepartment(deptEmp.getDeptNo()));

        deptEmpEntity.setFromDate(deptEmp.getFromDate());
        deptEmpEntity.setToDate(deptEmp.getToDate());

        deptEmpRepository.save(deptEmpEntity);
        log.trace("DeptEmp updated: {}", deptEmpEntity);
    }

    private DepartmentEntity queryDepartment(String deptNo) throws UnknownDepartmentException {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(deptNo);

        if (!departmentEntity.isPresent()) {
            UnknownDepartmentException departmentException = new UnknownDepartmentException("Department with Department number " + deptNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+departmentException.getMessage(),departmentException.getClass());
            throw departmentException;
        }
        return departmentEntity.get();
    }

    private EmployeeEntity queryEmployee(int empNo) throws UnknownEmployeeException {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(empNo);

        if (!employeeEntity.isPresent()) {

            UnknownEmployeeException employeeException = new UnknownEmployeeException("Employee with Employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+employeeException.getMessage(),employeeException.getClass());
            throw employeeException;
        }
        return employeeEntity.get();
    }

    private DeptEmpEntity queryDeptEmp(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptEmpException {
        Optional<DeptEmpEntity> deptEmpEntity = deptEmpRepository.findByEmpNoAndDeptNo(empNo, deptNo);

        if (!deptEmpEntity.isPresent()) {
            UnknownDeptEmpException deptEmpException = new UnknownDeptEmpException("DeptEmp with Employee number " + empNo + " and Department number "+ deptNo +" doesn't exist.");
            log.error("Exception: {} thrown with message: "+deptEmpException.getMessage(),deptEmpException.getClass());
            throw deptEmpException;
        }
        return deptEmpEntity.get();
    }
}