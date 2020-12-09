package employee.dao.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "departments", schema = "employees")

public class DepartmentEntity {

    @Id
    @Column(name = "dept_no")
    private String deptNo;


    @Column(name = "dept_name")
    private String deptName;


}
