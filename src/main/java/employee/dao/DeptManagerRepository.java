package employee.dao;

import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.DeptManagerEntity;
import employee.dao.entity.DeptManagerId;
import employee.dao.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface DeptManagerRepository extends CrudRepository<DeptManagerEntity, DeptManagerId> {

    @Transactional
    Optional<DeptManagerEntity> findByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo);

    @Transactional
    void deleteByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo);

}