package sun.vn.employee_management.config;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.vn.employee_management.dto.EmployeeResponse;
import sun.vn.employee_management.entity.Employee;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Employee.class, EmployeeResponse.class)
                .addMappings(mapper -> mapper
                        .map(Employee::getDepartment, EmployeeResponse::setDepartmentDto));
        return modelMapper;
    }
}
