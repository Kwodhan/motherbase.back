package com.motherbase.apirest.model.motherbase.department;

import javax.persistence.Entity;

@Entity
public class Barrack extends Department {
    public Barrack() {
        super();
        this.rank = RankDepartment.Level1;
    }
}
