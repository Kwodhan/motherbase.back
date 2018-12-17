package com.motherbase.apirest.model.motherbase.department;

import javax.persistence.Entity;

@Entity
public class Development extends Department {

    public Development() {
        super();
        this.rank = RankDepartment.Level0;
    }
}
