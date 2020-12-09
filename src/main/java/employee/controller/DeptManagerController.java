package employee.controller;

import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.EmployeeEntity;
import employee.exception.DeptManagerAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.exception.UnknownDeptManagerException;
import employee.exception.UnknownEmployeeException;
import employee.model.DeptManager;
import employee.services.DeptManagerService;
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
public class DeptManagerController {

    private final DeptManagerService service;

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
            service.recordDeptManager(new DeptManager(
                    requestDto.getEmpNo(),
                    requestDto.getDeptNo(),
                    requestDto.getFromDate(),
                    requestDto.getToDate()

            ));
        } catch (DeptManagerAlreadyExistsException | UnknownEmployeeException | UnknownDepartmentException e) {
            log.error("Exception: {} handled with message: " + e.getMessage(), e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "DeptManager successfully created";
    }

    @DeleteMapping("/delete/{empNo}/{deptNo}")
    public String deleteDeptManager(@PathVariable("empNo") EmployeeEntity empNo, @PathVariable("deptNo") DepartmentEntity deptNo) {
        try {
            service.deleteDeptManagerByEmpNoAndDeptNo(empNo, deptNo);
        } catch (UnknownDeptManagerException | UnknownEmployeeException | UnknownDepartmentException e) {
            log.error("Exception: {} handled with message: " + e.getMessage(), e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "DeptManager successfully deleted";
    }

    @PutMapping("/update")
    public String update(@Valid @RequestBody DeptManagerRequestDto deptManagerRequestDto) {
        try{
            service.updateDeptManager(new DeptManager(
                    deptManagerRequestDto.getEmpNo(),
                    deptManagerRequestDto.getDeptNo(),
                    deptManagerRequestDto.getFromDate(),
                    deptManagerRequestDto.getToDate()
            ));
        }catch (UnknownDeptManagerException | UnknownEmployeeException | UnknownDepartmentException e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "DeptManager successfully updated";
    }
}

