package employee.controller;

import employee.dao.entity.Department_entity;
import employee.dao.entity.Employee_entity;
import employee.exception.Dept_emp_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.exception.Unknown_dept_emp_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Dept_emp;
import employee.services.Dept_emp_service;
import employee.controller.dto.DeptEmpDto;
import employee.controller.dto.DeptEmpRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/dept_emp")
public class Dept_emp_controller {

    private final Dept_emp_service service;

    @GetMapping("/getAll")
    public Collection<DeptEmpDto> listDeptEmp(){
        return service.getAllDeptEmp()
                .stream()
                .map(model -> DeptEmpDto.builder()
                        .empNo(model.getEmpNo())
                        .deptNo(model.getDeptNo())
                        .fromDate(model.getFromDate())
                        .toDate(model.getToDate())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public String record(@Valid @RequestBody DeptEmpRequestDto requestDto) {

        try {
            service.recordDeptEmp(new Dept_emp(

                    requestDto.getEmpNo(),
                    requestDto.getDeptNo(),
                    requestDto.getFromDate(),
                    requestDto.getToDate()

            ));
        } catch (Dept_emp_already_exists_exception | Unknown_employee_exception | Unknown_department_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "DeptEmp successfully created";
    }

    @DeleteMapping("/delete/{empNo}/{deptNo}")
    public String deleteDeptEmp(@PathVariable("empNo") Employee_entity empNo, @PathVariable("deptNo") Department_entity deptNo)  {
        try{
            service.deleteDeptEmpByEmpNoAndDeptNo(empNo, deptNo);
        }catch (Unknown_dept_emp_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Unknown_department_exception | Unknown_employee_exception e) {
            e.printStackTrace();
        }
        return "DeptEmp successfully deleted";
    }


    @PutMapping("/update")
    public String update(@Valid @RequestBody DeptEmpRequestDto deptEmpRequestDto) {
        try{
            service.updateDeptEmp(new Dept_emp(
                    deptEmpRequestDto.getEmpNo(),
                    deptEmpRequestDto.getDeptNo(),
                    deptEmpRequestDto.getFromDate(),
                    deptEmpRequestDto.getToDate()
            ));
        }catch (Unknown_dept_emp_exception | Unknown_employee_exception | Unknown_department_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "DeptEmp successfully updated";
    }
}