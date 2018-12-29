package com.motherbase.apirest.model.staff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.mission.MissionInProgress;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Fighter {
    private Long id;
    private Boolean down;
    private MissionInProgress missionInProgress;
    private Integer force;


    public Fighter() {
        this.down = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @Transient
    public abstract Integer getForce();

    public void setForce(Integer force) {
        this.force = force;
    }

    public void setDown(Boolean down) {
        this.down = down;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public MissionInProgress getMissionInProgress() {
        return missionInProgress;
    }

    public void setMissionInProgress(MissionInProgress missionInProgress) {
        this.missionInProgress = missionInProgress;
    }

    @JsonIgnore
    public Boolean isDown() {
        return down;
    }

    @Transient
    @JsonIgnore
    public boolean isInMission() {
        return missionInProgress != null;
    }
}
