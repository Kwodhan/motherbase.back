package com.motherbase.apirest.model.staff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.mission.MissionInProgress;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Fighter {
    private Long id;
    private MissionInProgress missionInProgress;
    private Integer force;
    private boolean isDead;


    public Fighter() {
        this.isDead = false;
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



    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public MissionInProgress getMissionInProgress() {
        return missionInProgress;
    }

    public void setMissionInProgress(MissionInProgress missionInProgress) {
        this.missionInProgress = missionInProgress;
    }

    @Transient
    @JsonIgnore
    public abstract boolean canGoToMission();

    /**
     * take damage on fighter. return true if the fighter is dead
     *
     * @param successMission                  if the mission is sucess
     * @param mission                         the mission
     * @param multiplierDyingFailedMission    multiplierDying if the mission is failed. Define in jsonToParse/parameters.json
     * @param multiplierInjuringFailedMission multiplierInjuring if the mission is failed. Define in jsonToParse/parameters.json
     * @return if the Fighter is dead
     */
    @Transient
    @JsonIgnore
    public abstract void takeDamage(boolean successMission, Mission mission, double multiplierDyingFailedMission, double multiplierInjuringFailedMission);

    @Transient
    @JsonIgnore
    public abstract void dead();

    @Transient
    @JsonIgnore
    public boolean isInMission() {
        return missionInProgress != null;
    }

    @Transient
    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

}
