package com.example.service;

import com.example.dto.EmployeeRequest;
import com.example.dto.EmployeeResponse;

import com.example.entity.Department;
import com.example.entity.Employee;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.DepartmentRepository;
import com.example.repository.EmployeeRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest employeeReq) {
        Employee employee = modelMapper.map(employeeReq, Employee.class);

        Department department = departmentRepository.findById(employeeReq.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        employee.setDepartment(department);

        employeeRepository.save(employee);

        return modelMapper.map(employee, EmployeeResponse.class);
    }


    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(e -> modelMapper.map(e, EmployeeResponse.class)).toList();
    }

    public EmployeeResponse getEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeReq) {
        Employee employeeInDb = getEmployeeById(id);
        Department department = departmentRepository.findById(employeeReq.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        employeeInDb.setDepartment(department);
        modelMapper.map(employeeReq, employeeInDb);
        return modelMapper.map(employeeRepository.save(employeeInDb), EmployeeResponse.class);
    }

    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    public List<EmployeeResponse> searchByNameOrDepartment(String keyword) {
        return employeeRepository.searchByNameOrDepartment(keyword)
                .stream().map(e -> modelMapper.map(e, EmployeeResponse.class)).toList();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }
}
