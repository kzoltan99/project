package employee.controller;

import employee.exception.Employee_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Employee;
import employee.services.Employee_service;
import employee.controller.dto.EmployeeRequestDto;
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
@RequestMapping(path = "/employees")
public class Employee_controller {

    private final Employee_service service;

    @GetMapping("/getAll")
    public Collection<Employee> listEmployee(){
        return service.getAllEmployee()
                .stream()
                .map(model -> Employee.builder()
                        .empNo(model.getEmpNo())
                        .birthDate(model.getBirthDate())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .gender(model.getGender())
                        .hireDate(model.getHireDate())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public String record(@Valid @RequestBody EmployeeRequestDto requestDto) {

        try {
            service.recordEmployee(new Employee(
                    requestDto.getEmpNo(),
                    requestDto.getBirthDate(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getGender(),
                    requestDto.getHireDate()

            ));
        } catch (Employee_already_exists_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Employee successfully created";
    }

    @DeleteMapping("/delete/{empNo}")
    public String deleteEmployee(@PathVariable("empNo") int empNo)  {
        try{
            service.deleteEmployeeByEmpNo(empNo);
        }catch (Unknown_employee_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Employee successfully deleted";
    }


    @PutMapping("/update")
    public String update(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        try{
            service.updateEmployee(new Employee(
                    employeeRequestDto.getEmpNo(),
                    employeeRequestDto.getBirthDate(),
                    employeeRequestDto.getFirstName(),
                    employeeRequestDto.getLastName(),
                    employeeRequestDto.getGender(),
                    employeeRequestDto.getHireDate()
            ));
        }catch (Unknown_employee_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Employee successfully updated";
    }
}
