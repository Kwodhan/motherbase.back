package com.motherbase.apirest.service;

import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.motherbase.department.Department;
import com.motherbase.apirest.model.staff.Staff;

import java.util.List;

public interface MotherBaseService {
    MotherBase create(MotherBase motherBase);

    MotherBase findById(Long id);

    List<MotherBase> findByPseudo(String pseudo);

    boolean moveStaff(Staff staff, Department department);

    boolean upgrade(MotherBase motherBase, Department department);
}
