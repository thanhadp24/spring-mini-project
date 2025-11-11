package com.example.controller;

import com.example.dto.EmployeeRequest;
import com.example.entity.Department;
import com.example.entity.Employee;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import jakarta.validation.Valid;
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

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeRequest());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employees/add";
    }

    @PostMapping("/save")
    public String saveEmployee(
            @Valid @ModelAttribute("employee") EmployeeRequest employeeRequest,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employees/add";
        }

        employeeService.createEmployee(employeeRequest);
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam(required = false) String keyword, Model model) {
        if(keyword != null){
            model.addAttribute("employees", employeeService.searchByNameOrDepartment(keyword));
        }else {
            model.addAttribute("employees", employeeService.getAllEmployees());
        }
        return "employees/search";
    }


}
