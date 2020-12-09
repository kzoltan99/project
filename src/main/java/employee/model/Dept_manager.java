package employee.model;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@ToString
@Builder
@Getter
@EqualsAndHashCode
public class Dept_manager {

	private int empNo;
	private String deptNo;
	private Date fromDate;
	private Date toDate;
	
}
