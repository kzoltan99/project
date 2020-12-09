package employee.dao;

import employee.dao.entity.EmployeeEntity;
import employee.dao.entity.SalaryEntity;
import employee.dao.entity.SalaryId;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Optional;

public interface SalaryRepository extends CrudRepository<SalaryEntity, SalaryId> {

    Optional<SalaryEntity> findByEmpNo(EmployeeEntity empNo);

    @Transactional
    void deleteByEmpNoAndFromDate(EmployeeEntity empNo, Date fromDate);

}
