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

    /**
     * Begin the upgrade of the department with resources of motherBase
     *
     * @param motherBase the motherBase that will use its resources for upgrade
     * @param department the department that will upgrade
     * @return has begin upgrade
     */
    boolean beginUpgradeDepartment(MotherBase motherBase, Department department);

    /**
     * Upgrade the department
     *
     * @param motherBase the motherBase that will use its resources for upgrade
     * @param department the department that will upgrade
     * @return the upgrade is done
     */
    boolean finishUpgradeDepartment(MotherBase motherBase, Department department);


}
