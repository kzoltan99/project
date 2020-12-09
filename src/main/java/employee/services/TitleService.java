package employee.services;

import employee.dao.entity.EmployeeEntity;
import employee.exception.TitleAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.exception.UnknownTitleException;
import employee.model.Title;

import java.sql.Date;
import java.util.Collection;

public interface TitleService {

    Collection<Title> getAllTitle();
    void recordTitle(Title title) throws TitleAlreadyExistsException, UnknownEmployeeException;
    void deleteByEmpNoAndTitleAndFromDate(EmployeeEntity empNo, String title, Date fromDate) throws UnknownTitleException, UnknownEmployeeException;
    void updateTitle(Title title) throws UnknownTitleException, UnknownEmployeeException;

}
