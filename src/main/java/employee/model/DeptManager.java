package employee.model;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@ToString
@Builder
@Getter
@EqualsAndHashCode
public class DeptManager {

	private int empNo;
	private String deptNo;
	private Date fromDate;
	private Date toDate;
	
}
