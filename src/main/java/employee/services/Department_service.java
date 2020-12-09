package employee.services;

import employee.exception.Department_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.model.Department;

import java.util.Collection;

public interface Department_service {

    Collection<Department> getAllDepartment();
    void recordDepartment(Department department) throws Department_already_exists_exception;
    void deleteDepartmentByDeptNo(String deptNo) throws Unknown_department_exception;
    void updateDepartment(Department department) throws Unknown_department_exception;

}
