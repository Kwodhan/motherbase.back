package com.motherbase.apirest.model.motherbase.department;

import javax.persistence.Entity;

@Entity
public class RandD extends Department {

    public RandD() {
        super();
        this.rank = RankDepartment.Level0;
    }
}
