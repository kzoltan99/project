package employee.dao;

import employee.dao.entity.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity, String> {

    @Override
    Optional<DepartmentEntity> findById(String deptNo);

    @Override
    void deleteById(String deptNo);

}
