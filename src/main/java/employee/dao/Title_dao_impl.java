package employee.dao;

import employee.dao.entity.Employee_entity;
import employee.dao.entity.Title_entity;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class Title_dao_impl implements Title_dao {

    private final Title_repository titleRepository;
    private final Employee_repository employeeRepository;

    @Override
    public void createTitle(Title title) throws Title_already_exists_exception, Unknown_employee_exception {

        Title_entity titleEntity;

        try {
            titleEntity = queryTitle(queryEmployee(title.getEmpNo()));

        }
        catch (Unknown_title_exception e){

            titleEntity = Title_entity.builder()
                    .empNo(queryEmployee(title.getEmpNo()))
                    .title(title.getTitle())
                    .fromDate(title.getFromDate())
                    .toDate(title.getToDate())
                    .build();
            log.info("TitleEntity: {}", titleEntity);
            titleRepository.save(titleEntity);
            return;
        }
        Title_already_exists_exception TitleException = new Title_already_exists_exception("Title with Employee number " + title.getEmpNo()+" already exists.");
        log.error("Exception: {} thrown with message: "+TitleException.getMessage(),TitleException.getClass());
        throw TitleException;

    }



    @Override
    public Collection<Title> readAll() {
        return StreamSupport.stream(titleRepository.findAll().spliterator(),false)
                .map(entity -> new Title(
                        entity.getEmpNo().getEmpNo(),
                        entity.getTitle(),
                        entity.getFromDate(),
                        entity.getToDate()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public Title readByEmpNo(Employee_entity empNo) throws Unknown_title_exception {

        Title_entity titleEntity = queryTitle(empNo);
        log.trace("Title found by Employee number: {} Title: {}", empNo,titleEntity);
        return new Title(titleEntity.getEmpNo().getEmpNo(),titleEntity.getTitle(),titleEntity.getFromDate(),titleEntity.getToDate());

    }

    @Transactional
    public void deleteByEmpNoAndTitleAndFromDate(Employee_entity empNo, String title, Date fromDate) throws Unknown_title_exception, Unknown_employee_exception {

        Title_entity titleEntity = queryTitle(queryEmployee(empNo.getEmpNo()));
        titleRepository.deleteByEmpNoAndTitleAndFromDate(empNo, title, fromDate);
        log.trace("Title deleted: {}", titleEntity);

    }

    @Override
    public void update(Title title) throws Unknown_title_exception, Unknown_employee_exception {

        Title_entity titleEntity = queryTitle(queryEmployee(title.getEmpNo()));

        titleEntity.setEmpNo(queryEmployee(title.getEmpNo()));
        titleEntity.setTitle(title.getTitle());
        titleEntity.setFromDate(title.getFromDate());
        titleEntity.setToDate(title.getToDate());

        titleRepository.save(titleEntity);
        log.trace("Title updated: {}", titleEntity);
    }

    private Title_entity queryTitle(Employee_entity empNo) throws Unknown_title_exception {
        Optional<Title_entity> titleEntity = titleRepository.findByEmpNo(empNo);

        if (!titleEntity.isPresent()) {
            Unknown_title_exception titleException = new Unknown_title_exception("Title with Employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+titleException.getMessage(),titleException.getClass());
            throw titleException;
        }
        return titleEntity.get();
    }

    private Employee_entity queryEmployee(int empNo) throws Unknown_employee_exception {
        Optional<Employee_entity> employeeEntity = employeeRepository.findByEmpNo(empNo);

        if (!employeeEntity.isPresent()) {
            Unknown_employee_exception employeeException = new Unknown_employee_exception("Employee with employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+employeeException.getMessage(),employeeException.getClass());
            throw employeeException;
        }
        return employeeEntity.get();
    }
}
