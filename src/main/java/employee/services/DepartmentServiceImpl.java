package employee.services;

import employee.dao.DepartmentDao;
import employee.exception.DepartmentAlreadyExistsException;
import employee.exception.UnknownDepartmentException;
import employee.model.Department;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentDao departmentDao;

    @Override
    public Collection<Department> getAllDepartment() {
        return departmentDao.readAll();
    }

    @Override
    public void recordDepartment(Department department) throws DepartmentAlreadyExistsException {
        departmentDao.createDepartment(department);
    }
    @Override
    public void deleteDepartmentByDeptNo(String deptNo) throws UnknownDepartmentException {
        departmentDao.deleteByDeptNo(deptNo);
    }

    @Override
    public void updateDepartment(Department department) throws UnknownDepartmentException {
        departmentDao.update(department);
    }
}
