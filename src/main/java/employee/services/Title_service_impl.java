package employee.services;

import employee.dao.Title_dao;
import employee.dao.entity.Employee_entity;
import employee.exception.Title_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.exception.Unknown_title_exception;
import employee.model.Title;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class Title_service_impl implements Title_service {

    private final Title_dao titleDao;

    @Override
    public Collection<Title> getAllTitle() {
        return titleDao.readAll();
    }

    @Override
    public void recordTitle(Title Title) throws Title_already_exists_exception, Unknown_employee_exception {
        titleDao.createTitle(Title);
    }
    @Transactional
    public void deleteByEmpNoAndTitleAndFromDate(Employee_entity empNo, String title, Date fromDate) throws Unknown_title_exception, Unknown_employee_exception {
        titleDao.deleteByEmpNoAndTitleAndFromDate(empNo,title,fromDate);
    }

    @Override
    public void updateTitle(Title Title) throws Unknown_title_exception, Unknown_employee_exception {
        titleDao.update(Title);
    }
}