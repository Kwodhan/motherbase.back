package com.motherbase.apirest.service;

import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.motherbase.department.Department;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.repository.MotherBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MotherBaseServiceImpl implements MotherBaseService {

    @Autowired
    private MotherBaseRepository motherBaseRepository;




    public MotherBaseServiceImpl() {
    }

    @Override
    @Transactional
    public MotherBase create(MotherBase motherBase) {

        return motherBaseRepository.save(motherBase);
    }

    @Override
    public MotherBase findById(Long id) {
        return motherBaseRepository.findById(id).orElse(null);
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
        return true;

    }

    @Override
    @Transactional
    public boolean beginUpgradeDepartment(MotherBase motherBase, Department department) {
        if (motherBase.canUpgrade(department)) {
            motherBase.beginUpgrade(department);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean finishUpgradeDepartment(MotherBase motherBase, Department department) {
        if (motherBase.isFinishUpgrade(department)) {
            motherBase.upgrade(department);
            return true;
        }
        return false;

    }



}
