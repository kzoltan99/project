package employee.dao;

import employee.dao.entity.Department_entity;
import employee.dao.entity.Dept_manager_entity;
import employee.dao.entity.Dept_manager_id;
import employee.dao.entity.Employee_entity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface Dept_manager_repository extends CrudRepository<Dept_manager_entity, Dept_manager_id> {

    @Transactional
    Optional<Dept_manager_entity> findByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo);

    @Transactional
    void deleteByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo);

}