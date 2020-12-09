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
@Table(name="dept_emp", schema="employees")
@IdClass(Dept_emp_id.class)
public class Dept_emp_entity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ManyToOne
    @JoinColumn(name ="emp_no")
    private Employee_entity empNo;

    @Id
    @ManyToOne
    @JoinColumn(name ="dept_no")
    private Department_entity deptNo;

    @Column(name ="from_date")
    private Date fromDate;

    @Column(name ="to_date")
    private Date toDate;


}