package employee.services;

import employee.dao.DeptManagerDao;
import employee.dao.entity.DepartmentEntity;
import employee.dao.entity.EmployeeEntity;
import employee.exception.DeptManagerAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.exception.UnknownDeptManagerException;
import employee.exception.UnknownEmployeeException;
import employee.model.DeptManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeptManagerServiceImpl implements DeptManagerService{

    private final DeptManagerDao deptManagerDao;

    @Override
    public Collection<DeptManager> getAllDeptManager() {
        return deptManagerDao.readAll();
    }

    @Override
    public void recordDeptManager(DeptManager deptManager) throws DeptManagerAlreadyExistsException, UnknownEmployeeException, UnknownDepartmentException {
        deptManagerDao.createDeptManager(deptManager);
    }

    @Override
    public void deleteDeptManagerByEmpNoAndDeptNo(EmployeeEntity empNo, DepartmentEntity deptNo) throws UnknownDeptManagerException, UnknownEmployeeException, UnknownDepartmentException {
        deptManagerDao.deleteByEmpNoAndDeptNo(empNo, deptNo);
    }

    @Override
    public void updateDeptManager(DeptManager deptManager) throws UnknownDeptManagerException, UnknownEmployeeException, UnknownDepartmentException {
        deptManagerDao.update(deptManager);
    }
}
