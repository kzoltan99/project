package employee.dao;

import employee.dao.entity.Employee_entity;
import employee.dao.entity.Title_entity;
import employee.dao.entity.Title_id;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Optional;

public interface Title_repository extends CrudRepository<Title_entity, Title_id> {

    Optional<Title_entity> findByEmpNo(Employee_entity empNo);

    @Transactional
    void deleteByEmpNoAndTitleAndFromDate(Employee_entity empNo, String title, Date fromDate);

}