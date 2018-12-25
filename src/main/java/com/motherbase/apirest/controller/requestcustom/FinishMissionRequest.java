package com.motherbase.apirest.controller.requestcustom;

public class FinishMissionRequest {
    private Long idMotherBase;
    private Long idMission;

    public Long getIdMotherBase() {
        return idMotherBase;
    }

    public void setIdMotherBase(Long idMotherBase) {
        this.idMotherBase = idMotherBase;
    }

    public Long getIdMission() {
        return idMission;
    }

    public void setIdMission(Long idMission) {
        this.idMission = idMission;
    }
}
