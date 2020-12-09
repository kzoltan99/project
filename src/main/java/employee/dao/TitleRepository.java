package employee.dao;

import employee.dao.entity.EmployeeEntity;
import employee.dao.entity.TitleEntity;
import employee.dao.entity.TitleId;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Optional;

public interface TitleRepository extends CrudRepository<TitleEntity, TitleId> {

    Optional<TitleEntity> findByEmpNo(EmployeeEntity empNo);

    @Transactional
    void deleteByEmpNoAndTitleAndFromDate(EmployeeEntity empNo, String title, Date fromDate);

}