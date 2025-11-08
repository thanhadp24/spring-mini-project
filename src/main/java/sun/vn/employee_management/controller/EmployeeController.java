package sun.vn.employee_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sun.vn.employee_management.entity.Employee;
import sun.vn.employee_management.service.UtilityService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilityService utilityService;

    private final List<Employee> employees = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employee.setId(utilityService.generateEmployeeId());
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employees.add(employee);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employees);
    }
}
