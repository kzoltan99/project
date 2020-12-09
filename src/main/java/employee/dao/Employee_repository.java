package employee.dao;

import employee.dao.entity.Employee_entity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface Employee_repository extends CrudRepository<Employee_entity, Integer> {

    @Transactional
    Optional<Employee_entity> findByEmpNo(int empNo);

    @Transactional
    void deleteByEmpNo(int empNo);

}