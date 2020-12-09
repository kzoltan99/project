package employee.model;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@ToString
@Builder
@Getter
@EqualsAndHashCode
public class Title {

	private int empNo;
	private String title;
	private Date fromDate;
	private Date toDate;
	
}
