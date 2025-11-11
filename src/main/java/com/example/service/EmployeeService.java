package com.example.service;

import com.example.dto.EmployeeRequest;
import com.example.dto.EmployeeResponse;
import com.example.entity.Department;
import com.example.entity.Employee;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.DepartmentRepository;
import com.example.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger("EMPLOYEE_SERVICE");

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest employeeReq) {
        logger.info("Creating new employee: {}", employeeReq.getName());
        Employee employee = modelMapper.map(employeeReq, Employee.class);

        Department department = departmentRepository.findById(employeeReq.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        employee.setDepartment(department);

        employeeRepository.save(employee);
        logger.info("Employee created with id: {}", employee.getId());

        return modelMapper.map(employee, EmployeeResponse.class);
    }


    public List<EmployeeResponse> getAllEmployees() {
        logger.info("Fetching all employees");
        return employeeRepository.findAll().stream()
                .map(e -> modelMapper.map(e, EmployeeResponse.class)).toList();
    }

    public EmployeeResponse getEmployee(Long id) {
        logger.info("Fetching employee with id: {}", id);
        Employee employee = getEmployeeById(id);
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeReq) {
        logger.info("Updating employee with id: {}", id);
        Employee employeeInDb = getEmployeeById(id);
        Department department = departmentRepository.findById(employeeReq.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        employeeInDb.setDepartment(department);
        modelMapper.map(employeeReq, employeeInDb);
        logger.info("Employee with id: {} updated successfully", id);
        return modelMapper.map(employeeRepository.save(employeeInDb), EmployeeResponse.class);
    }

    public void deleteEmployee(Long id) {
        logger.info("Deleting employee with id: {}", id);
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
        logger.info("Employee with id: {} deleted successfully", id);
    }

    public List<EmployeeResponse> searchByNameOrDepartment(String keyword) {
        logger.info("Searching employees by keyword: {}", keyword);
        return employeeRepository.searchByNameOrDepartment(keyword)
                .stream().map(e -> modelMapper.map(e, EmployeeResponse.class)).toList();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }
}
