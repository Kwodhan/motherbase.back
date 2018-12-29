package com.motherbase.apirest.service;

import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Staff create(Staff staff) {
        return staffRepository.save(staff);
    }

    @Transactional
    public Staff update(Staff staff) {
        Staff updatedStaff = staffRepository.findById(staff.getId()).orElse(null);
        updatedStaff.setMissionInProgress(staff.getMissionInProgress());
        updatedStaff.setDepartment(staff.getDepartment());
        updatedStaff.setName(staff.getName());
        updatedStaff.setSkillSet(staff.getSkillSet());
        updatedStaff.setDown(staff.isDown());
        updatedStaff.setForce(staff.getForce());

        return updatedStaff;
    }

    @Override
    public Staff findById(Long id) {
        return staffRepository.findById(id).orElse(null);
    }
}
