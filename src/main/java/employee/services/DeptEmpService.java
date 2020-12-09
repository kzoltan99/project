package employee.services;

import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.EmployeeEntity;
import employee.exception.DeptEmpAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.exception.UnknownDeptEmpException;
import employee.exception.UnknownEmployeeException;
import employee.model.DeptEmp;

import java.util.Collection;

public interface DeptEmpService {

    Collection<DeptEmp> getAllDeptEmp();
    void recordDeptEmp(DeptEmp deptEmp) throws DeptEmpAlreadyExistsException, UnknownEmployeeException, UnknownDepartmentException;
    void deleteDeptEmpByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptEmpException,UnknownEmployeeException, UnknownDepartmentException;
    void updateDeptEmp(DeptEmp deptEmp) throws UnknownDeptEmpException, UnknownEmployeeException, UnknownDepartmentException;

}
