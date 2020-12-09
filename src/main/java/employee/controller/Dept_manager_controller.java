package employee.controller;

import employee.dao.entity.Department_entity;
import employee.dao.entity.Employee_entity;
import employee.exception.Dept_manager_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.exception.Unknown_dept_manager_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Dept_manager;
import employee.services.Dept_manager_service;
import employee.controller.dto.DeptManagerDto;
import employee.controller.dto.DeptManagerRequestDto;
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
@RequestMapping(path = "/dept_manager")
public class Dept_manager_controller {

    private final Dept_manager_service service;

    @GetMapping("/getAll")
    public Collection<DeptManagerDto> listDeptManager() {
        return service.getAllDeptManager()
                .stream()
                .map(model -> DeptManagerDto.builder()
                        .empNo(model.getEmpNo())
                        .deptNo(model.getDeptNo())
                        .fromDate(model.getFromDate())
                        .toDate(model.getToDate())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public String record(@Valid @RequestBody DeptManagerRequestDto requestDto) {

        try {
            service.recordDeptManager(new Dept_manager(
                    requestDto.getEmpNo(),
                    requestDto.getDeptNo(),
                    requestDto.getFromDate(),
                    requestDto.getToDate()

            ));
        } catch (Dept_manager_already_exists_exception | Unknown_employee_exception | Unknown_department_exception e) {
            log.error("Exception: {} handled with message: " + e.getMessage(), e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "DeptManager successfully created";
    }

    @DeleteMapping("/delete/{empNo}/{deptNo}")
    public String deleteDeptManager(@PathVariable("empNo") Employee_entity empNo, @PathVariable("deptNo") Department_entity deptNo) {
        try {
            service.deleteDeptManagerByEmpNoAndDeptNo(empNo, deptNo);
        } catch (Unknown_dept_manager_exception | Unknown_employee_exception | Unknown_department_exception e) {
            log.error("Exception: {} handled with message: " + e.getMessage(), e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "DeptManager successfully deleted";
    }

    @PutMapping("/update")
    public String update(@Valid @RequestBody DeptManagerRequestDto deptManagerRequestDto) {
        try{
            service.updateDeptManager(new Dept_manager(
                    deptManagerRequestDto.getEmpNo(),
                    deptManagerRequestDto.getDeptNo(),
                    deptManagerRequestDto.getFromDate(),
                    deptManagerRequestDto.getToDate()
            ));
        }catch (Unknown_dept_manager_exception | Unknown_employee_exception | Unknown_department_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "DeptManager successfully updated";
    }
}

