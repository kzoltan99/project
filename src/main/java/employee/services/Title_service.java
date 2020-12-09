package employee.services;

import employee.dao.entity.Employee_entity;
import employee.exception.Title_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.exception.Unknown_title_exception;
import employee.model.Title;

import java.sql.Date;
import java.util.Collection;

public interface Title_service {

    Collection<Title> getAllTitle();
    void recordTitle(Title title) throws Title_already_exists_exception, Unknown_employee_exception;
    void deleteByEmpNoAndTitleAndFromDate(Employee_entity empNo, String title, Date fromDate) throws Unknown_title_exception, Unknown_employee_exception;
    void updateTitle(Title title) throws Unknown_title_exception, Unknown_employee_exception;

}
