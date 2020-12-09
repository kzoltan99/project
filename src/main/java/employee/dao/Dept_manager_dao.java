package employee.dao;

import employee.dao.entity.Department_entity;
import employee.dao.entity.Employee_entity;
import employee.exception.Dept_manager_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.exception.Unknown_dept_manager_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Dept_manager;

import java.util.Collection;

public interface Dept_manager_dao {

    Collection<Dept_manager> readAll();
    Dept_manager readByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_manager_exception;
    void createDeptManager(Dept_manager deptManager) throws Dept_manager_already_exists_exception, Unknown_employee_exception, Unknown_department_exception;
    void deleteByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_manager_exception;
    void update(Dept_manager deptManager) throws Unknown_dept_manager_exception, Unknown_employee_exception, Unknown_department_exception;

}
