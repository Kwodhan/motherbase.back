package com.motherbase.apirest.model.staff.vehicule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.motherbase.Garage;
import com.motherbase.apirest.model.staff.Fighter;

import javax.persistence.*;

@Entity
public class Vehicle extends Fighter {
    private Garage garage;
    private TypeVehicle typeVehicle;

    public Vehicle(TypeVehicle typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public Vehicle() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Override
    @Transient
    public Integer getForce() {
        return typeVehicle.getForce();
    }

    @Enumerated(EnumType.ORDINAL)
    public TypeVehicle getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(TypeVehicle typeVehicle) {
        this.typeVehicle = typeVehicle;
    }
}
