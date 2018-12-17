package com.motherbase.apirest.model.motherbase.department;

import javax.persistence.Entity;

@Entity
public class WaitingRoom extends Department {
    public WaitingRoom() {
        super();
        this.rank = RankDepartment.Level0;
    }
}
