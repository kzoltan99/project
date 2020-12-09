package employee.dao;

import employee.dao.entity.Employee_entity;
import employee.exception.Title_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.exception.Unknown_title_exception;
import employee.model.Title;

import java.sql.Date;
import java.util.Collection;

public interface Title_dao {

    Collection<Title> readAll();
    Title readByEmpNo(Employee_entity empNo) throws Unknown_title_exception;
    void createTitle(Title title) throws Title_already_exists_exception, Unknown_employee_exception;
    void deleteByEmpNoAndTitleAndFromDate(Employee_entity empNo, String title, Date fromDate) throws Unknown_title_exception, Unknown_employee_exception;
    void update(Title title) throws Unknown_title_exception, Unknown_employee_exception;

}
