package employee.dao;

import employee.dao.entity.Department_entity;
import employee.dao.entity.Dept_emp_entity;
import employee.dao.entity.Dept_emp_id;
import employee.dao.entity.Employee_entity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface Dept_emp_repository extends CrudRepository<Dept_emp_entity, Dept_emp_id> {

    @Transactional
    Optional<Dept_emp_entity> findByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo);

    @Transactional
    void deleteByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo);

}