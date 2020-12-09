package employee.dao;

import employee.dao.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {

    @Transactional
    Optional<EmployeeEntity> findByEmpNo(int empNo);

    @Transactional
    void deleteByEmpNo(int empNo);

}