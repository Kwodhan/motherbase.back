package com.motherbase.apirest.model.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.resource.Resource;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Mission {
    private Long id;

    private Map<Resource, Integer> rewardResources;
    private Set<RewardStaff> rewardStaffs;
    /**
     * reward resource not sure
     */
    private Set<RewardResource> percentRewardResources;


    private Integer force;

    private Integer rankMission;

    private String description;

    private Duration durationCompleted;

    private Set<MissionInProgress> missionInProgresses;


    public Mission() {
    }

    public Mission(Integer force, String description, Integer rankMission, Duration durationCompleted, Set<RewardStaff> rewardStaffs, Set<RewardResource> percentRewardResources, Integer... rewards) {
        if (rewards.length != Resource.values().length) {
            throw new IllegalArgumentException("There are not the same number between rewards arguments and number of resources in Resource enum ");
        }
        this.force = force;
        this.description = description;
        this.rankMission = rankMission;
        this.durationCompleted = durationCompleted;
        this.percentRewardResources = percentRewardResources;
        for (RewardStaff rewardStaff : rewardStaffs) {
            rewardStaff.setMission(this);
        }
        for (RewardResource rewardResource : percentRewardResources) {
            rewardResource.setMission(this);
        }
        this.rewardStaffs = rewardStaffs;
        this.rewardResources = new HashMap<>();
        int indexResource = 0;
        for (Resource resource : Resource.values()) {
            this.rewardResources.put(resource, rewards[indexResource++]);
        }
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @MapKeyClass(Resource.class)
    @MapKeyEnumerated(EnumType.ORDINAL)
    public Map<Resource, Integer> getRewardResources() {
        return rewardResources;
    }

    public void setRewardResources(Map<Resource, Integer> rewardResources) {
        this.rewardResources = rewardResources;
    }

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<RewardResource> getPercentRewardResources() {
        return percentRewardResources;
    }

    public void setPercentRewardResources(Set<RewardResource> percentRewardResources) {
        this.percentRewardResources = percentRewardResources;
    }

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<RewardStaff> getRewardStaffs() {
        return rewardStaffs;
    }

    public void setRewardStaffs(Set<RewardStaff> rewardStaffs) {
        this.rewardStaffs = rewardStaffs;
    }

    public Integer getForce() {
        return force;
    }

    public void setForce(Integer force) {
        this.force = force;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
