package employee.dao;

import employee.exception.DepartmentAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.model.Department;

import java.util.Collection;

public interface DepartmentDao {

    Collection<Department> readAll();
    Department readByDeptNo(String deptNo) throws UnknownDepartmentException;
    void createDepartment(Department department) throws DepartmentAlreadyExistsException;
    void deleteByDeptNo(String deptNo) throws UnknownDepartmentException;
    void update(Department department) throws UnknownDepartmentException;

}
