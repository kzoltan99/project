package employee.dao;

import employee.dao.entity.EmployeeEntity;
import employee.exception.TitleAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.exception.UnknownTitleException;
import employee.model.Title;

import java.sql.Date;
import java.util.Collection;

public interface TitleDao {

    Collection<Title> readAll();
    Title readByEmpNo(EmployeeEntity empNo) throws UnknownTitleException;
    void createTitle(Title title) throws TitleAlreadyExistsException, UnknownEmployeeException;
    void deleteByEmpNoAndTitleAndFromDate(EmployeeEntity empNo, String title, Date fromDate) throws UnknownTitleException, UnknownEmployeeException;
    void update(Title title) throws UnknownTitleException, UnknownEmployeeException;

}
