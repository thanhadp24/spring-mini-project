package sun.vn.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sun.vn.employee_management.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %?1% OR e.department.name LIKE %?1%")
    List<Employee> searchByNameOrDepartment(String keyword);
}
