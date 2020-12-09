package employee.services;

import employee.dao.EmployeeDao;
import employee.exception.EmployeeAlreadyExistsException;
import employee.exception.UnknownEmployeeException;
import employee.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeDao employeeDao;

    @Override
    public Collection<Employee> getAllEmployee() {
        return employeeDao.readAll();
    }

    @Override
    public void recordEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        employeeDao.createEmployee(employee);
    }

    @Override
    public void deleteEmployeeByEmpNo(int empNo) throws UnknownEmployeeException {
        employeeDao.deleteByEmpNo(empNo);
    }

    @Override
    public void updateEmployee(Employee employee) throws UnknownEmployeeException {
        employeeDao.update(employee);
    }
}