package employee.dao;

import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.EmployeeEntity;
import employee.exception.DeptManagerAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.exception.UnknownDeptManagerException;
import employee.exception.UnknownEmployeeException;
import employee.model.DeptManager;

import java.util.Collection;

public interface DeptManagerDao {

    Collection<DeptManager> readAll();
    DeptManager readByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptManagerException;
    void createDeptManager(DeptManager deptManager) throws DeptManagerAlreadyExistsException, UnknownEmployeeException, UnknownDepartmentException;
    void deleteByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptManagerException;
    void update(DeptManager deptManager) throws UnknownDeptManagerException, UnknownEmployeeException, UnknownDepartmentException;

}
