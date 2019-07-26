package com.motherbase.apirest.model.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Duration;
import java.util.Set;

@Entity
public class Mission {
    private Long id;

    private Set<RewardResource> rewardResources;
    private Set<RewardStaff> rewardStaffs;
    private Set<RewardVehicle> rewardVehicles;

    private Integer force;

    private Integer rankMission;

    private String description;

    private Duration durationCompleted;

    private Integer maxPercentageSuccess;

    private Integer chanceDying;

    private Integer chanceInjuring;

    private Set<MissionInProgress> missionInProgresses;


    public Mission() {
    }

    public Mission(Integer force, String description, Integer rankMission, Duration durationCompleted,
                   Set<RewardStaff> rewardStaffs, Set<RewardResource> rewardResources, Set<RewardVehicle> rewardVehicles) {
        this(force, description, rankMission, durationCompleted, rewardStaffs, rewardResources);

        for (RewardVehicle rewardVehicle : rewardVehicles) {
            rewardVehicle.setMission(this);
        }
        this.rewardVehicles = rewardVehicles;
    }


    public Mission(Integer force, String description, Integer rankMission, Duration durationCompleted,
                   Set<RewardStaff> rewardStaffs, Set<RewardResource> rewardResources) {
        this.force = force;
        this.description = description;
        this.rankMission = rankMission;
        this.durationCompleted = durationCompleted;
        this.maxPercentageSuccess = 100;
        this.chanceDying = 5;
        this.chanceInjuring = 10;

        for (RewardStaff rewardStaff : rewardStaffs) {
            rewardStaff.setMission(this);
        }
        for (RewardResource rewardResource : rewardResources) {
            rewardResource.setMission(this);
        }

        this.rewardStaffs = rewardStaffs;
        this.rewardResources = rewardResources;

    }

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<RewardResource> getRewardResources() {
        return rewardResources;
    }

    public void setRewardResources(Set<RewardResource> rewardResources) {
        this.rewardResources = rewardResources;
    }

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<RewardStaff> getRewardStaffs() {
        return rewardStaffs;
    }

    public void setRewardStaffs(Set<RewardStaff> rewardStaffs) {
        this.rewardStaffs = rewardStaffs;
    }

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<RewardVehicle> getRewardVehicles() {
        return rewardVehicles;
    }

    public void setRewardVehicles(Set<RewardVehicle> rewardVehicles) {
        this.rewardVehicles = rewardVehicles;
    }

    public Integer getForce() {
        return force;
    }

    public void setForce(Integer force) {
        this.force = force;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Integer getRankMission() {
        return rankMission;
    }

    public void setRankMission(Integer rankMission) {
        this.rankMission = rankMission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getDurationCompleted() {
        return durationCompleted;
    }

    public void setDurationCompleted(Duration durationCompleted) {
        this.durationCompleted = durationCompleted;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "mission")
    public Set<MissionInProgress> getMissionInProgresses() {
        return missionInProgresses;
    }

    public void setMissionInProgresses(Set<MissionInProgress> missionInProgresses) {
        this.missionInProgresses = missionInProgresses;
    }

    @JsonIgnore
    public Integer getMaxPercentageSuccess() {
        return maxPercentageSuccess;
    }

    public void setMaxPercentageSuccess(Integer maxPercentageSuccess) {
        this.maxPercentageSuccess = maxPercentageSuccess;
    }

    @JsonIgnore
    public Integer getChanceDying() {
        return chanceDying;
    }

    public void setChanceDying(Integer chanceDying) {
        this.chanceDying = chanceDying;
    }

    @JsonIgnore
    public Integer getChanceInjuring() {
        return chanceInjuring;
    }

    public void setChanceInjuring(Integer chanceInjuring) {
        this.chanceInjuring = chanceInjuring;
    }
}
