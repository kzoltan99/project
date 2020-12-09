package employee.dao;

import employee.exception.Department_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.model.Department;

import java.util.Collection;

public interface Department_dao {

    Collection<Department> readAll();
    Department readByDeptNo(String deptNo) throws Unknown_department_exception;
    void createDepartment(Department department) throws Department_already_exists_exception;
    void deleteByDeptNo(String deptNo) throws Unknown_department_exception;
    void update(Department department) throws Unknown_department_exception;

}
