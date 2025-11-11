package com.example.controller;//package sun.vn.employee_management.controller;

import com.example.dto.EmployeeRequest;
import com.example.dto.EmployeeResponse;
import com.example.service.EmployeeService;
import com.example.service.UtilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeReq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeReq));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id,@Valid @RequestBody EmployeeRequest employeeReq) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> searchByNameOrDepartment(@RequestParam String keyword) {
        return ResponseEntity.ok(employeeService.searchByNameOrDepartment(keyword));
    }
}
