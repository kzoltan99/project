package employee.dao;

import employee.dao.entity.Department_entity;
import employee.exception.Department_already_exists_exception;
import employee.exception.Unknown_department_exception;
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
public class Department_dao_impl implements Department_dao {

    private final Department_repository departmentRepository;

    @Override
    public void createDepartment(Department department) throws Department_already_exists_exception {

        Department_entity departmentEntity;

        try {
            departmentEntity = queryDepartment(department.getDeptNo());

        }
        catch (Unknown_department_exception e){

        departmentEntity = Department_entity.builder()
                .deptNo(department.getDeptNo())
                .deptName(department.getDeptName())
                .build();
        log.info("DepartmentEntity: {}", departmentEntity);
        departmentRepository.save(departmentEntity);
        return;
    }
    Department_already_exists_exception departmentException = new Department_already_exists_exception("Department with department number " + department.getDeptNo()+" already exists.");
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
    public Department readByDeptNo(String deptNo) throws Unknown_department_exception {

        Department_entity departmentEntity = queryDepartment(deptNo);
        log.trace("Department found by department number: {} department: {}", deptNo,departmentEntity);
        return new Department(departmentEntity.getDeptNo(),departmentEntity.getDeptName());

    }

    @Override
    public void deleteByDeptNo(String deptNo) throws Unknown_department_exception {

        Department_entity departmentEntity = queryDepartment(deptNo);
        departmentRepository.deleteById(deptNo);
        log.trace("Department deleted: {}", departmentEntity);

    }

    @Override
    public void update( Department department) throws Unknown_department_exception {

        Department_entity departmentEntity = queryDepartment(department.getDeptNo());

        departmentEntity.setDeptNo(department.getDeptNo());
        departmentEntity.setDeptName(department.getDeptName());

        departmentRepository.save(departmentEntity);
        log.trace("Department updated: {}", departmentEntity);
    }

    private Department_entity queryDepartment(String deptNo) throws Unknown_department_exception {
        Optional<Department_entity> departmentEntity = departmentRepository.findById(deptNo);

        if (!departmentEntity.isPresent()) {
            Unknown_department_exception departmentException = new Unknown_department_exception("Department with department number " + deptNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+departmentException.getMessage(),departmentException.getClass());
            throw departmentException;
        }
        return departmentEntity.get();
    }
}
