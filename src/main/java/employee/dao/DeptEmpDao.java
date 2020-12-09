package employee.dao;

import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.EmployeeEntity;
import employee.exception.DeptEmpAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.exception.UnknownDeptEmpException;
import employee.exception.UnknownEmployeeException;
import employee.model.DeptEmp;

import java.util.Collection;

public interface DeptEmpDao {

    Collection<DeptEmp> readAll();
    DeptEmp readByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptEmpException;
    void createDeptEmp(DeptEmp deptEmp) throws DeptEmpAlreadyExistsException, UnknownEmployeeException, UnknownDepartmentException;
    void deleteByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptEmpException;
    void update(DeptEmp deptEmp) throws UnknownDeptEmpException, UnknownEmployeeException, UnknownDepartmentException;

}
