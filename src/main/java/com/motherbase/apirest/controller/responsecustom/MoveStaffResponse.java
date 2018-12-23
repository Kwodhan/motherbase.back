package com.motherbase.apirest.controller.responsecustom;

import com.motherbase.apirest.model.motherbase.MotherBase;

/**
 *
 */
public class MoveStaffResponse extends CustomerResponse {
    private MotherBase motherBase;

    public MoveStaffResponse(MotherBase motherBase) {
        this.motherBase = motherBase;
    }

    public MoveStaffResponse() {
    }

    public MotherBase getMotherBase() {
        return motherBase;
    }

    public void setMotherBase(MotherBase motherBase) {
        this.motherBase = motherBase;
    }
}
