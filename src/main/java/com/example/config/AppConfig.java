package com.example.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        modelMapper.createTypeMap(Employee.class, EmployeeResponse.class)
//                .addMappings(mapper -> mapper
//                        .map(Employee::getDepartment, EmployeeResponse::setDepartment));
        return modelMapper;
    }

}
