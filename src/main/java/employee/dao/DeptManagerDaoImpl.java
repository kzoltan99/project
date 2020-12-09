package employee.dao;

import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.DeptManagerEntity;
import employee.dao.entity.EmployeeEntity;
import employee.exception.DeptManagerAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.exception.UnknownDeptManagerException;
import employee.exception.UnknownEmployeeException;
import employee.model.DeptManager;
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
public class DeptManagerDaoImpl implements DeptManagerDao {

    private final DeptManagerRepository deptManagerRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void createDeptManager(DeptManager deptManager) throws DeptManagerAlreadyExistsException, UnknownEmployeeException, UnknownDepartmentException {

        DeptManagerEntity deptManagerEntity;

        try {
            deptManagerEntity = queryDeptManager(queryEmployee(deptManager.getEmpNo()),queryDepartment(deptManager.getDeptNo()));

        }
        catch (UnknownDeptManagerException e){

            deptManagerEntity = DeptManagerEntity.builder()
                    .empNo(queryEmployee(deptManager.getEmpNo()))
                    .deptNo(queryDepartment(deptManager.getDeptNo()))
                    .fromDate(deptManager.getFromDate())
                    .toDate(deptManager.getToDate())
                    .build();
            log.info("DeptManagerEntity: {}", deptManagerEntity);
            deptManagerRepository.save(deptManagerEntity);
            return;
        }
        DeptManagerAlreadyExistsException deptManagerException = new DeptManagerAlreadyExistsException("DeptManager " + deptManager.getEmpNo()+" already exists.");
        log.error("Exception: {} thrown with message: "+deptManagerException.getMessage(),deptManagerException.getClass());
        throw deptManagerException;

    }



    @Override
    public Collection<DeptManager> readAll() {
        return StreamSupport.stream(deptManagerRepository.findAll().spliterator(),false)
                .map(entity -> new DeptManager(
                        entity.getEmpNo().getEmpNo(),
                        entity.getDeptNo().getDeptNo(),
                        entity.getFromDate(),
                        entity.getToDate()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public DeptManager readByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptManagerException {

        DeptManagerEntity deptManagerEntity = queryDeptManager(empNo,deptNo);
        log.trace("DeptManager found by employee number: {} and department number: {}",empNo,deptNo);
        return new DeptManager(deptManagerEntity.getEmpNo().getEmpNo(), deptManagerEntity.getDeptNo().getDeptNo(),
                deptManagerEntity.getFromDate(),deptManagerEntity.getToDate());

    }

    @Override
    public void deleteByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptManagerException {

        DeptManagerEntity deptManagerEntity = queryDeptManager(empNo, deptNo);
        deptManagerRepository.deleteByEmpNoAndDeptNo(empNo, deptNo);
        log.trace("DeptManager deleted: {}", deptManagerEntity);

    }

    @Override
    public void update(DeptManager deptManager) throws UnknownDeptManagerException, UnknownEmployeeException,UnknownDepartmentException {

        DeptManagerEntity deptManagerEntity;

        deptManagerEntity = queryDeptManager(queryEmployee(deptManager.getEmpNo()),queryDepartment(deptManager.getDeptNo()));

        deptManagerEntity.setFromDate(deptManager.getFromDate());
        deptManagerEntity.setToDate(deptManager.getToDate());

        deptManagerRepository.save(deptManagerEntity);
        log.trace("DeptManager updated: {}", deptManagerEntity);
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

    private DeptManagerEntity queryDeptManager(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptManagerException {
        Optional<DeptManagerEntity> deptManagerEntity = deptManagerRepository.findByEmpNoAndDeptNo(empNo, deptNo);

        if (!deptManagerEntity.isPresent()) {
            UnknownDeptManagerException deptManagerException = new UnknownDeptManagerException("DeptManager with Employee number " + empNo + " and Department number "+ deptNo +" doesn't exist.");
            log.error("Exception: {} thrown with message: "+deptManagerException.getMessage(),deptManagerException.getClass());
            throw deptManagerException;
        }
        return deptManagerEntity.get();
    }
}