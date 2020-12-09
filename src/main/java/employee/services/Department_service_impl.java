package employee.services;

import employee.dao.Department_dao;
import employee.exception.Department_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.model.Department;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Slf4j
@Service
@RequiredArgsConstructor
public class Department_service_impl implements Department_service {

    private final Department_dao departmentDao;

    @Override
    public Collection<Department> getAllDepartment() {
        return departmentDao.readAll();
    }

    @Override
    public void recordDepartment(Department department) throws Department_already_exists_exception {
        departmentDao.createDepartment(department);
    }
    @Override
    public void deleteDepartmentByDeptNo(String deptNo) throws Unknown_department_exception {
        departmentDao.deleteByDeptNo(deptNo);
    }

    @Override
    public void updateDepartment(Department department) throws Unknown_department_exception {
        departmentDao.update(department);
    }
}
