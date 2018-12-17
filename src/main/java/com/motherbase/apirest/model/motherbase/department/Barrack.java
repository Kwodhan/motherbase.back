package com.motherbase.apirest.model.motherbase.department;

import com.motherbase.apirest.model.staff.RankStaff;
import com.motherbase.apirest.model.staff.Staff;

import javax.persistence.Entity;

@Entity
public class Barrack extends Department {
    public Barrack() {
        super();
        this.rank = RankDepartment.Level1;
        this.listStaff.add(new Staff("Charles", RankStaff.S, RankStaff.S, RankStaff.S, RankStaff.S, this));
    }
}
