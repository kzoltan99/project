package employee.dao;

import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.DeptEmpEntity;
import employee.dao.entity.DeptEmpId;
import employee.dao.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface DeptEmpRepository extends CrudRepository<DeptEmpEntity, DeptEmpId> {

    @Transactional
    Optional<DeptEmpEntity> findByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo);

    @Transactional
    void deleteByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo);

}