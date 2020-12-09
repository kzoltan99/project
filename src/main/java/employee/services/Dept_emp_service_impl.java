package employee.services;

import employee.dao.Dept_emp_dao;
import employee.dao.entity.Department_entity;
import employee.dao.entity.Employee_entity;
import employee.exception.Dept_emp_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.exception.Unknown_dept_emp_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Dept_emp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class Dept_emp_service_impl implements Dept_emp_service {

    private final Dept_emp_dao deptEmpDao;

    @Override
    public Collection<Dept_emp> getAllDeptEmp() {
        return deptEmpDao.readAll();
    }

    @Override
    public void recordDeptEmp(Dept_emp deptEmp) throws Dept_emp_already_exists_exception, Unknown_employee_exception, Unknown_department_exception {
        deptEmpDao.createDeptEmp(deptEmp);
    }

    @Override
    public void deleteDeptEmpByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_emp_exception, Unknown_employee_exception, Unknown_department_exception {
        deptEmpDao.deleteByEmpNoAndDeptNo(empNo, deptNo);
    }

    @Override
    public void updateDeptEmp(Dept_emp deptEmp) throws Unknown_dept_emp_exception, Unknown_employee_exception, Unknown_department_exception {
        deptEmpDao.update(deptEmp);
    }
}