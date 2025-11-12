package com.example.controller;//package sun.vn.employee_management.controller;

import com.example.dto.DepartmentStatisticDto;
import com.example.dto.EmployeeRequest;
import com.example.dto.EmployeeResponse;
import com.example.service.EmployeeService;
import com.example.service.UtilityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger("EMPLOYEE_CONTROLLER");

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeReq) {
        logger.info("Received request to create employee: {}", employeeReq.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeReq));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        logger.info("Received request to fetch all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
        logger.info("Received request to fetch employee with id: {}", id);
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id,@Valid @RequestBody EmployeeRequest employeeReq) {
        logger.info("Received request to update employee with id: {}", id);
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeReq));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        logger.info("Received request to delete employee with id: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> searchByNameOrDepartment(@RequestParam String keyword) {
        logger.info("Received request to search employees with keyword: {}", keyword);
        return ResponseEntity.ok(employeeService.searchByNameOrDepartment(keyword));
    }

    @GetMapping("/total-employees")
    public ResponseEntity<Long> getTotalEmployees() {
        logger.info("Received request to get total number of employees");
        Long totalEmployees = employeeService.countTotalEmployees();
        return ResponseEntity.ok(totalEmployees);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<DepartmentStatisticDto>> getDepartmentStatistics() {
        logger.info("Received request to get department statistics");
        return ResponseEntity.ok(employeeService.getDepartmentStatistics());
    }
}
