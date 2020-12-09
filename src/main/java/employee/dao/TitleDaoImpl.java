package employee.dao;

import employee.dao.entity.EmployeeEntity;
import employee.dao.entity.TitleEntity;
import employee.exception.TitleAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.exception.UnknownTitleException;
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
public class TitleDaoImpl implements TitleDao {

    private final TitleRepository titleRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void createTitle(Title title) throws TitleAlreadyExistsException, UnknownEmployeeException {

        TitleEntity titleEntity;

        try {
            titleEntity = queryTitle(queryEmployee(title.getEmpNo()));

        }
        catch (UnknownTitleException e){

            titleEntity = TitleEntity.builder()
                    .empNo(queryEmployee(title.getEmpNo()))
                    .title(title.getTitle())
                    .fromDate(title.getFromDate())
                    .toDate(title.getToDate())
                    .build();
            log.info("TitleEntity: {}", titleEntity);
            titleRepository.save(titleEntity);
            return;
        }
        TitleAlreadyExistsException TitleException = new TitleAlreadyExistsException("Title with Employee number " + title.getEmpNo()+" already exists.");
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
    public Title readByEmpNo(EmployeeEntity empNo) throws UnknownTitleException {

        TitleEntity titleEntity = queryTitle(empNo);
        log.trace("Title found by Employee number: {} Title: {}", empNo,titleEntity);
        return new Title(titleEntity.getEmpNo().getEmpNo(),titleEntity.getTitle(),titleEntity.getFromDate(),titleEntity.getToDate());

    }

    @Transactional
    public void deleteByEmpNoAndTitleAndFromDate(EmployeeEntity empNo, String title, Date fromDate) throws UnknownTitleException, UnknownEmployeeException {

        TitleEntity titleEntity = queryTitle(queryEmployee(empNo.getEmpNo()));
        titleRepository.deleteByEmpNoAndTitleAndFromDate(empNo, title, fromDate);
        log.trace("Title deleted: {}", titleEntity);

    }

    @Override
    public void update(Title title) throws UnknownTitleException, UnknownEmployeeException {

        TitleEntity titleEntity = queryTitle(queryEmployee(title.getEmpNo()));

        titleEntity.setEmpNo(queryEmployee(title.getEmpNo()));
        titleEntity.setTitle(title.getTitle());
        titleEntity.setFromDate(title.getFromDate());
        titleEntity.setToDate(title.getToDate());

        titleRepository.save(titleEntity);
        log.trace("Title updated: {}", titleEntity);
    }

    private TitleEntity queryTitle(EmployeeEntity empNo) throws UnknownTitleException {
        Optional<TitleEntity> titleEntity = titleRepository.findByEmpNo(empNo);

        if (!titleEntity.isPresent()) {
            UnknownTitleException titleException = new UnknownTitleException("Title with Employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+titleException.getMessage(),titleException.getClass());
            throw titleException;
        }
        return titleEntity.get();
    }

    private EmployeeEntity queryEmployee(int empNo) throws UnknownEmployeeException {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findByEmpNo(empNo);

        if (!employeeEntity.isPresent()) {
            UnknownEmployeeException employeeException = new UnknownEmployeeException("Employee with employee number " + empNo + " doesn't exist.");
            log.error("Exception: {} thrown with message: "+employeeException.getMessage(),employeeException.getClass());
            throw employeeException;
        }
        return employeeEntity.get();
    }
}
