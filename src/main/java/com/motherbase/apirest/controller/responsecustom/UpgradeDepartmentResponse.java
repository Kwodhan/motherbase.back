package com.motherbase.apirest.controller.responsecustom;

import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.motherbase.department.Department;

import java.time.Duration;

public class UpgradeDepartmentResponse extends CustomerResponse {

    private Duration durationUpgrade;
    private Department department;
    private MotherBase motherBase;


    public UpgradeDepartmentResponse(Duration durationUpgrade, Department department, MotherBase motherBase) {
        this.durationUpgrade = durationUpgrade;
        this.department = department;
        this.motherBase = motherBase;
    }

    public UpgradeDepartmentResponse() {
    }

    public Duration getDurationUpgrade() {
        return durationUpgrade;
    }

    public void setDurationUpgrade(Duration durationUpgrade) {
        this.durationUpgrade = durationUpgrade;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public MotherBase getMotherBase() {
        return motherBase;
    }

    public void setMotherBase(MotherBase motherBase) {
        this.motherBase = motherBase;
    }

}
