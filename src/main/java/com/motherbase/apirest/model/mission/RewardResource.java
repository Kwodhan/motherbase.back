package com.motherbase.apirest.model.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.resource.Resource;

import javax.persistence.*;

@Entity
@Table(name = "MISSION_REWARD_RESOURCES_PERCENT")
public class RewardResource {
    Long id;
    Resource resource;
    Integer number;
    Integer percent;
    Mission mission;

    public RewardResource(Long id, Resource resource, Integer number, Integer percent, Mission mission) {
        this.id = id;
        this.resource = resource;
        this.number = number;
        this.percent = percent;
        this.mission = mission;
    }

    public RewardResource() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Enumerated(EnumType.ORDINAL)
    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @ManyToOne
    @JsonIgnore
    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }
}
