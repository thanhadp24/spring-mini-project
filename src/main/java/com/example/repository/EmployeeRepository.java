package com.example.repository;

import com.example.dto.DepartmentStatisticDto;
import com.example.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %?1% OR e.department.name LIKE %?1%")
    List<Employee> searchByNameOrDepartment(String keyword);

    @Query(value = "SELECT d.name departmentName, COUNT(e.id) employeeCount FROM employees e JOIN departments d ON e.department_id = d.id " +
                   "GROUP BY d.name ORDER BY COUNT(e.id) DESC", nativeQuery = true)
    List<DepartmentStatisticDto> getDepartmentStatistics();
}
