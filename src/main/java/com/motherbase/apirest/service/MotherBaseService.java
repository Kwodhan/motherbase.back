package com.motherbase.apirest.service;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.motherbase.department.Department;
import com.motherbase.apirest.model.staff.Staff;

import java.util.List;

public interface MotherBaseService {
    MotherBase create(MotherBase motherBase);

    MotherBase findById(Long id);

    List<MotherBase> findByPseudo(String pseudo);

    boolean moveStaff(Staff staff, Department department);

    boolean triggerUpgradeDepartment(MotherBase motherBase, Department department);

    Department upgradeDepartment(MotherBase motherBase, Department department);

    boolean takeMission(MotherBase motherBase, Mission mission, List<Long> fighters);

    boolean finishMission(MotherBase motherBase, Mission mission);
}
