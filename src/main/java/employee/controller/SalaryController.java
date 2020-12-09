package employee.controller;

import employee.dao.entity.EmployeeEntity;
import employee.exception.SalaryAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.model.Salary;
import employee.services.SalaryService;
import employee.controller.dto.SalaryDto;
import employee.controller.dto.SalaryRequestDto;
import employee.exception.UnknownSalaryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/salaries")
public class SalaryController {

    private final SalaryService service;

    @GetMapping("/getAll")
    public Collection<SalaryDto> listSalary(){
        return service.getAllSalary()
                .stream()
                .map(model -> SalaryDto.builder()
                        .empNo(model.getEmpNo())
                        .salary(model.getSalary())
                        .fromDate(model.getFromDate())
                        .toDate(model.getToDate())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public String record(@Valid @RequestBody SalaryRequestDto requestDto) {

        try {
            service.recordSalary(new Salary(
                    requestDto.getEmpNo(),
                    requestDto.getSalary(),
                    requestDto.getFromDate(),
                    requestDto.getToDate()

            ));
        } catch (SalaryAlreadyExistsException | UnknownEmployeeException e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Salary successfully created";
    }

    @DeleteMapping("/delete/{empNo}/{fromDate}")
    public String deleteTitleByEmpNo(@PathVariable("empNo") EmployeeEntity empNo,
                                     @PathVariable("fromDate") Date fromDate)  {
        try{
            service.deleteByEmpNoAndFromDate(empNo,fromDate);
        }catch (UnknownEmployeeException | UnknownSalaryException e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Salary successfully deleted";
    }


    @PutMapping("/update")
    public String update(@Valid @RequestBody SalaryRequestDto salaryDto) {
        try{
            service.updateSalary(new Salary(
                    salaryDto.getEmpNo(),
                    salaryDto.getSalary(),
                    salaryDto.getFromDate(),
                    salaryDto.getToDate()
            ));
        }catch (UnknownSalaryException | UnknownEmployeeException e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Salary successfully updated";
    }
}
