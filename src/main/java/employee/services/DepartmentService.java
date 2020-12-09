package employee.services;

import employee.exception.DepartmentAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.model.Department;

import java.util.Collection;

public interface DepartmentService {

    Collection<Department> getAllDepartment();
    void recordDepartment(Department department) throws DepartmentAlreadyExistsException;
    void deleteDepartmentByDeptNo(String deptNo) throws UnknownDepartmentException;
    void updateDepartment(Department department) throws UnknownDepartmentException;

}
