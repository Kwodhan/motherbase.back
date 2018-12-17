package com.motherbase.apirest.model.motherbase.department;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class WaitingRoom extends Department {
    public WaitingRoom() {
        super();
        this.rank = RankDepartment.Level4;
    }

    @Override
    public void upgradeRank() {
    }

    @Transient
    @Override
    public boolean isPossibleToAddStuff() {
        return true;
    }

    @Transient
    @JsonIgnore
    public Integer getMaxStaff() {
        return this.rank.getMaxStuff();
    }
}
