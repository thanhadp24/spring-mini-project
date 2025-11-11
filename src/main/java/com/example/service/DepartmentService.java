package com.example.service;

import com.example.dto.DepartmentDto;
import com.example.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().
                stream().map(d -> modelMapper.map(d, DepartmentDto.class)).toList();
    }
}
