package employee.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name="dept_manager", schema="employees")
@IdClass(DeptManagerId.class)
public class DeptManagerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ManyToOne
    @JoinColumn(name ="emp_no")
    private EmployeeEntity empNo;

    @Id
    @ManyToOne
    @JoinColumn(name ="dept_no")
    private DepartmentEntity deptNo;

    @Column(name ="from_date")
    private Date fromDate;

    @Column(name ="to_date")
    private Date toDate;


}