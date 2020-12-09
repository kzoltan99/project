package employee.services;

import employee.dao.DeptEmpDao;
import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.EmployeeEntity;
import employee.exception.DeptEmpAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.exception.UnknownDeptEmpException;
import employee.exception.UnknownEmployeeException;
import employee.model.DeptEmp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeptEmpServiceImpl implements DeptEmpService{

    private final DeptEmpDao deptEmpDao;

    @Override
    public Collection<DeptEmp> getAllDeptEmp() {
        return deptEmpDao.readAll();
    }

    @Override
    public void recordDeptEmp(DeptEmp deptEmp) throws DeptEmpAlreadyExistsException, UnknownEmployeeException, UnknownDepartmentException {
        deptEmpDao.createDeptEmp(deptEmp);
    }

    @Override
    public void deleteDeptEmpByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptEmpException, UnknownEmployeeException, UnknownDepartmentException {
        deptEmpDao.deleteByEmpNoAndDeptNo(empNo, deptNo);
    }

    @Override
    public void updateDeptEmp(DeptEmp deptEmp) throws UnknownDeptEmpException, UnknownEmployeeException, UnknownDepartmentException {
        deptEmpDao.update(deptEmp);
    }
}