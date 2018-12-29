package com.motherbase.apirest.service;

import com.motherbase.apirest.model.motherbase.department.Department;
import com.motherbase.apirest.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;


    @Override
    public Department findWithMotherBaseById(Long id) {
        return departmentRepository.findDepartmentWithMotherBaseById(id);
    }

    @Override
    public Department findById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }
}
