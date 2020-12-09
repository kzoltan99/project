package employee.dao;

import employee.dao.entity.Department_entity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface Department_repository extends CrudRepository<Department_entity, String> {

    @Override
    Optional<Department_entity> findById(String deptNo);

    @Override
    void deleteById(String deptNo);

}
