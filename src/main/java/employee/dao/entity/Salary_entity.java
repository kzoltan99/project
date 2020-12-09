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
@Table(name="salaries", schema="employees")
@IdClass(Salary_id.class)
public class Salary_entity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ManyToOne
    @JoinColumn(name ="emp_no")
    private Employee_entity empNo;


    @Column(name = "salary")
    private int salary;

    @Id
    @Column(name ="from_date")
    private Date fromDate;

    @Column(name ="to_date")
    private Date toDate;


}
