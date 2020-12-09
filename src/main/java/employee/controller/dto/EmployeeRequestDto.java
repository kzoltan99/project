package employee.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto extends EmployeeDto{

    private Date birthDate;
    private String firstName;
    private String lastName;
    private String gender;
    private Date hireDate;

}
