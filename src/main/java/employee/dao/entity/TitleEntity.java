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
@Table(name="titles", schema="employees")
@IdClass(TitleId.class)
public class TitleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ManyToOne
    @JoinColumn(name ="emp_no")
    private EmployeeEntity empNo;

    @Id
    @Column(name="title")
    private String title;

    @Id
    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;



}
