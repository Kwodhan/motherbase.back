package com.motherbase.apirest.controller.requestcustom;

import java.util.List;

public class BeginMissionRequest {
    private Long idMotherBase;
    private Long idMission;
    private List<Long> fighterList;

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

    public List<Long> getFighterList() {
        return fighterList;
    }

    public void setFighterList(List<Long> fighterList) {
        this.fighterList = fighterList;
    }
}
