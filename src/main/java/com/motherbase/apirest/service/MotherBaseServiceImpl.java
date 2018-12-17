package com.motherbase.apirest.service;

import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.motherbase.department.Department;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.repository.DepartmentRepository;
import com.motherbase.apirest.repository.MotherBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MotherBaseServiceImpl implements MotherBaseService {

    @Autowired
    private MotherBaseRepository motherBaseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public MotherBaseServiceImpl() {
    }

    @Override
    @Transactional
    public MotherBase create(MotherBase motherBase) {

        return motherBaseRepository.save(motherBase);
    }

    @Override
    public MotherBase findById(Long id) {
        return motherBaseRepository.findMotherBaseById(id);
    }

    @Override
    public List<MotherBase> findByPseudo(String pseudo) {
        return motherBaseRepository.findMotherBaseByPseudo(pseudo);
    }


    @Override
    @Transactional
    public boolean moveStaff(Staff staff, Department department) {
        if (!department.isPossibleToAddStuff()) {
            return false;
        }
        staff.getDepartment().removeStaff(staff);
        department.addStaff(staff);
        staff.setDepartment(department);
        return true;

    }

    @Override
    @Transactional
    public boolean upgrade(MotherBase motherBase, Department department) {
        if (motherBase.canUpgrade(department)) {
            motherBase.upgrade(department);
            return true;
        }
        return false;
    }


}
