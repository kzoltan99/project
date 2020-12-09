package employee.model;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@ToString
@Builder
@Getter
@EqualsAndHashCode
public class Salary {

	private int empNo;
	private int salary;
	private Date fromDate;
	private Date toDate;
	
}
