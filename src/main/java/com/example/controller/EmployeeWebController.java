package com.example.controller;

import com.example.dto.EmployeeRequest;
import com.example.entity.Department;
import com.example.entity.Employee;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    private static final Logger logger = LoggerFactory.getLogger("EMPLOYEE_WEB_CONTROLLER");

    @GetMapping
    public String listEmployees(Model model) {
        logger.info("Listing all employees");
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        logger.info("Showing form to create new employee");
        model.addAttribute("employee", new EmployeeRequest());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employees/add";
    }

    @PostMapping("/save")
    public String saveEmployee(
            @Valid @ModelAttribute("employee") EmployeeRequest employeeRequest,
            BindingResult bindingResult,
            Model model) {
        logger.info("Saving new employee: {}", employeeRequest.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employees/add";
        }

        employeeService.createEmployee(employeeRequest);
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam(required = false) String keyword, Model model) {
        logger.info("Searching employees with keyword: {}", keyword);
        if(keyword != null){
            model.addAttribute("employees", employeeService.searchByNameOrDepartment(keyword));
        }else {
            model.addAttribute("employees", employeeService.getAllEmployees());
        }
        return "employees/search";
    }


}
