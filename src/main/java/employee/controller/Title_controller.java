package employee.controller;

import employee.dao.entity.Employee_entity;
import employee.exception.Title_already_exists_exception;
import employee.exception.Unknown_employee_exception;
import employee.exception.Unknown_title_exception;
import employee.model.Title;
import employee.services.Title_service;
import employee.controller.dto.TitleDto;
import employee.controller.dto.TitleRequestDto;
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
@RequestMapping(path = "/titles")
public class Title_controller {

    private final Title_service service;

    @GetMapping("/getAll")
    public Collection<TitleDto> listTitle(){
        return service.getAllTitle()
                .stream()
                .map(model -> TitleDto.builder()
                        .empNo(model.getEmpNo())
                        .title(model.getTitle())
                        .fromDate(model.getFromDate())
                        .toDate(model.getToDate())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public String record(@Valid @RequestBody TitleRequestDto requestDto) {

        try {
            service.recordTitle(new Title(
                    requestDto.getEmpNo(),
                    requestDto.getTitle(),
                    requestDto.getFromDate(),
                    requestDto.getToDate()

            ));
        } catch (Title_already_exists_exception | Unknown_employee_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Title successfully created";
    }

    @DeleteMapping("/delete/{empNo}/{title}/{fromDate}")
    public String deleteTitleByEmpNo(@PathVariable("empNo") Employee_entity empNo, @PathVariable("title") String title,
                                     @PathVariable("fromDate") Date fromDate)  {
        try{
            service.deleteByEmpNoAndTitleAndFromDate(empNo,title,fromDate);
        }catch (Unknown_title_exception | Unknown_employee_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Title successfully deleted";
    }


    @PutMapping("/update")
    public String update(@Valid @RequestBody TitleRequestDto titleDto) {
        try{
            service.updateTitle(new Title(
                    titleDto.getEmpNo(),
                    titleDto.getTitle(),
                    titleDto.getFromDate(),
                    titleDto.getToDate()
            ));
        }catch (Unknown_title_exception | Unknown_employee_exception e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Title successfully updated";
    }
}