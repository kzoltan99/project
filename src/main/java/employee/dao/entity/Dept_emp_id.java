package employee.dao.entity;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Dept_emp_id implements Serializable {

    private int empNo;

    private String deptNo;

}
