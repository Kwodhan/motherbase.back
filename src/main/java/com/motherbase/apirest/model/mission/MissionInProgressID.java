package com.motherbase.apirest.model.mission;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MissionInProgressID implements Serializable {

    private Long missionId;
    private Long motherBaseId;

    public MissionInProgressID() {
    }

    public MissionInProgressID(Long missionId, Long motherBaseId) {
        this.missionId = missionId;
        this.motherBaseId = motherBaseId;
    }

    @Column(name = "id_mission")
    public Long getMissionId() {
        return missionId;
    }


    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    @Column(name = "id_mother_base")
    public Long getMotherBaseId() {
        return motherBaseId;
    }

    public void setMotherBaseId(Long motherBaseId) {
        this.motherBaseId = motherBaseId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof MissionInProgressID)) return false;

        final MissionInProgressID cat = (MissionInProgressID) other;

        if (!cat.getMissionId().equals(getMissionId())) return false;
        return cat.getMotherBaseId().equals(getMotherBaseId());
    }

    @Override
    public int hashCode() {
        int result;
        result = 29 * getMissionId().hashCode() + 32 * getMotherBaseId().hashCode();
        return result;
    }


}
