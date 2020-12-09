package employee.dao;

import employee.dao.entity.Employee_entity;
import employee.dao.entity.Salary_entity;
import employee.dao.entity.Salary_id;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Optional;

public interface Salary_repository extends CrudRepository<Salary_entity, Salary_id> {

    Optional<Salary_entity> findByEmpNo(Employee_entity empNo);

    @Transactional
    void deleteByEmpNoAndFromDate(Employee_entity empNo, Date fromDate);

}
