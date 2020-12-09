package employee.controller;

import employee.dao.entity.EmployeeEntity;
import employee.exception.TitleAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.exception.UnknownTitleException;
import employee.model.Title;
import employee.services.TitleService;
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
public class TitleController {

    private final TitleService service;

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
        } catch (TitleAlreadyExistsException | UnknownEmployeeException e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Title successfully created";
    }

    @DeleteMapping("/delete/{empNo}/{title}/{fromDate}")
    public String deleteTitleByEmpNo(@PathVariable("empNo") EmployeeEntity empNo, @PathVariable("title") String title,
                                     @PathVariable("fromDate") Date fromDate)  {
        try{
            service.deleteByEmpNoAndTitleAndFromDate(empNo,title,fromDate);
        }catch (UnknownTitleException | UnknownEmployeeException e){
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
        }catch (UnknownTitleException | UnknownEmployeeException e){
            log.error("Exception: {} handled with message: "+e.getMessage(),e.getClass());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return "Title successfully updated";
    }
}