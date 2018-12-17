package com.motherbase.apirest.model.motherbase.department;

import javax.persistence.Entity;

@Entity
public class Infirmary extends Department {

    public Infirmary() {
        super();
        this.rank = RankDepartment.Level0;
    }
}
