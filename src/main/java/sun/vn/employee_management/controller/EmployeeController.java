package sun.vn.employee_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.vn.employee_management.entity.Employee;
import sun.vn.employee_management.service.UtilityService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilityService utilityService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employee.setId(utilityService.generateEmployeeId());
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        return ResponseEntity.ok(employee);
    }
}
