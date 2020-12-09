package employee.controller;

import employee.dao.entity.Employee_entity;
import employee.exception.Salary_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Salary;
import employee.services.Salary_service;
import employee.controller.dto.SalaryDto;
import employee.controller.dto.SalaryRequestDto;
import employee.exception.Unknown_salary_exception;
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
public class Salary_controller {

    private final Salary_service service;

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
        } catch (Salary_already_exists_exception | Unknown_employee_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Salary successfully created";
    }

    @DeleteMapping("/delete/{empNo}/{fromDate}")
    public String deleteTitleByEmpNo(@PathVariable("empNo") Employee_entity empNo,
                                     @PathVariable("fromDate") Date fromDate)  {
        try{
            service.deleteByEmpNoAndFromDate(empNo,fromDate);
        }catch (Unknown_employee_exception | Unknown_salary_exception e){
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
        }catch (Unknown_salary_exception | Unknown_employee_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Salary successfully updated";
    }
}
