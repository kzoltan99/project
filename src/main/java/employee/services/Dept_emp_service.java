package employee.services;

import employee.dao.entity.Department_entity;
import employee.dao.entity.Employee_entity;
import employee.exception.Dept_emp_already_exists_exception;
import employee.exception.Unknown_department_exception;
import employee.exception.Unknown_dept_emp_exception;
import employee.exception.Unknown_employee_exception;
import employee.model.Dept_emp;

import java.util.Collection;

public interface Dept_emp_service {

    Collection<Dept_emp> getAllDeptEmp();
    void recordDeptEmp(Dept_emp deptEmp) throws Dept_emp_already_exists_exception, Unknown_employee_exception, Unknown_department_exception;
    void deleteDeptEmpByEmpNoAndDeptNo(Employee_entity empNo, Department_entity deptNo) throws Unknown_dept_emp_exception, Unknown_employee_exception, Unknown_department_exception;
    void updateDeptEmp(Dept_emp deptEmp) throws Unknown_dept_emp_exception, Unknown_employee_exception, Unknown_department_exception;

}
