package employee.services;

import employee.dao.Dept_manager_dao;
import employee.dao.entity.Department_entity;
import employee.dao.entity.Employee_entity;
import employee.exception.Dept_manager_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.exception.Unknown_dept_manager_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Dept_manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class Dept_manager_service_impl implements Dept_manager_service {

    private final Dept_manager_dao deptManagerDao;

    @Override
    public Collection<Dept_manager> getAllDeptManager() {
        return deptManagerDao.readAll();
    }

    @Override
    public void recordDeptManager(Dept_manager deptManager) throws Dept_manager_already_exists_exception, Unknown_employee_exception, Unknown_department_exception {
        deptManagerDao.createDeptManager(deptManager);
    }

    @Override
    public void deleteDeptManagerByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_manager_exception, Unknown_employee_exception, Unknown_department_exception {
        deptManagerDao.deleteByEmpNoAndDeptNo(empNo, deptNo);
    }

    @Override
    public void updateDeptManager(Dept_manager deptManager) throws Unknown_dept_manager_exception, Unknown_employee_exception, Unknown_department_exception {
        deptManagerDao.update(deptManager);
    }
}
