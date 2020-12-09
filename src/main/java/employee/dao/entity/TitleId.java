package employee.dao.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TitleId implements Serializable {

    private int empNo;
    private String title;
    private Date fromDate;

}
