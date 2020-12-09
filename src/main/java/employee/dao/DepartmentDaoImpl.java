package employee.dao;

import employee.dao.entity.DepartmentEntity;
import employee.exception.DepartmentAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.model.Department;
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
public class DepartmentDaoImpl implements DepartmentDao {

    private final DepartmentRepository departmentRepository;

    @Override
    public void createDepartment(Department department) throws DepartmentAlreadyExistsException {

        DepartmentEntity departmentEntity;

        try {
            departmentEntity = queryDepartment(department.getDeptNo());

        }
        catch (UnknownDepartmentException e){

        departmentEntity = DepartmentEntity.builder()
                .deptNo(department.getDeptNo())
                .deptName(department.getDeptName())
                .build();
        log.info("DepartmentEntity: {}", departmentEntity);
        departmentRepository.save(departmentEntity);
        return;
    }
    DepartmentAlreadyExistsException departmentException = new DepartmentAlreadyExistsException("Department with department number " + department.getDeptNo()+" already exists.");
        log.error("Exception: {} thrown with message: "+departmentException.getMessage(),departmentException.getClass());
        throw departmentException;

    }



    @Override
    public Collection<Department> readAll() {
        return StreamSupport.stream(departmentRepository.findAll().spliterator(),false)
                .map(entity -> new Department(
                        entity.getDeptNo(),
                        entity.getDeptName()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public Department readByDeptNo(String deptNo) throws UnknownDepartmentException {

        DepartmentEntity departmentEntity = queryDepartment(deptNo);
        log.trace("Department found by department number: {} department: {}", deptNo,departmentEntity);
        return new Department(departmentEntity.getDeptNo(),departmentEntity.getDeptName());

    }

    @Override
    public void deleteByDeptNo(String deptNo) throws UnknownDepartmentException {

        DepartmentEntity departmentEntity = queryDepartment(deptNo);
        departmentRepository.deleteById(deptNo);
        log.trace("Department deleted: {}", departmentEntity);

    }

    @Override
    public void update( Department department) throws UnknownDepartmentException {

        DepartmentEntity departmentEntity = queryDepartment(department.getDeptNo());

        departmentEntity.setDeptNo(department.getDeptNo());
        departmentEntity.setDeptName(department.getDeptName());

        departmentRepository.save(departmentEntity);
        log.trace("Department updated: {}", departmentEntity);
    }

    private DepartmentEntity queryDepartment(String deptNo) throws UnknownDepartmentException {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(deptNo);

        if (!departmentEntity.isPresent()) {
            UnknownDepartmentException departmentException = new UnknownDepartmentException("Department with department number " + deptNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+departmentException.getMessage(),departmentException.getClass());
            throw departmentException;
        }
        return departmentEntity.get();
    }
}
