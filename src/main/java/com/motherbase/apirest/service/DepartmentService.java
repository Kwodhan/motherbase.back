package com.motherbase.apirest.service;

import com.motherbase.apirest.model.motherbase.department.Department;

public interface DepartmentService {

    Department findWithMotherBaseById(Long id);

    Department findById(Long id);
}
