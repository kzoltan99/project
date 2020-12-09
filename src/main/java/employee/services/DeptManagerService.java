package employee.services;

import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.EmployeeEntity;
import employee.exception.DeptManagerAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.exception.UnknownDeptManagerException;
import employee.exception.UnknownEmployeeException;
import employee.model.DeptManager;

import java.util.Collection;

public interface DeptManagerService {

    Collection<DeptManager> getAllDeptManager();
    void recordDeptManager(DeptManager deptManager) throws DeptManagerAlreadyExistsException, UnknownEmployeeException, UnknownDepartmentException;
    void deleteDeptManagerByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptManagerException,UnknownEmployeeException, UnknownDepartmentException;
    void updateDeptManager(DeptManager deptManager) throws UnknownDeptManagerException, UnknownEmployeeException, UnknownDepartmentException;

}
