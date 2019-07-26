package com.motherbase.apirest.model.mission;

import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.staff.Fighter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class MissionInProgress {


    private MissionInProgressID id;
    private Mission mission;
    private Date dateBegin;
    private MotherBase motherBase;
    private List<Fighter> fighters;

    public MissionInProgress(Mission mission, Date dateBegin, MotherBase motherBase, List<Fighter> fighters) {
        this.id = new MissionInProgressID(mission.getId(), motherBase.getId());
        this.mission = mission;
        this.dateBegin = dateBegin;
        this.motherBase = motherBase;
        this.fighters = fighters;

    }

    public MissionInProgress() {
    }

    @MapsId("missionId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mission")
    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    @MapsId("motherBaseId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mother_base")
    public MotherBase getMotherBase() {
        return motherBase;
    }

    public void setMotherBase(MotherBase motherBase) {
        this.motherBase = motherBase;
    }

    @EmbeddedId
    public MissionInProgressID getId() {
        return id;
    }

    public void setId(MissionInProgressID id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "missionInProgress")
    public List<Fighter> getFighters() {
        return fighters;
    }

    public void setFighters(List<Fighter> fighters) {
        this.fighters = fighters;
    }


}
