package com.motherbase.apirest.model.staff.vehicule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.motherbase.Garage;
import com.motherbase.apirest.model.staff.Fighter;

import javax.persistence.*;

@Entity
public class Vehicle extends Fighter {
    private Garage garage;
    private TypeVehicle typeVehicle;
    private Integer heath;

    public Vehicle(TypeVehicle typeVehicle) {
        this.typeVehicle = typeVehicle;
        this.heath = 100;
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

    @Override
    @JsonIgnore
    @Transient
    public boolean canGoToMission() {
        return true;
    }

    @Override
    public void takeDamage(boolean successMission, Mission mission, double multiplierDyingFailedMission, double multiplierInjuringFailedMission) {
        if (successMission) {
            this.heath -= mission.getChanceInjuring();
        } else {
            this.heath -= (int) ((double) mission.getChanceInjuring() * (multiplierInjuringFailedMission));
        }
        this.setDead(this.heath <= 0);
    }

    @Enumerated(EnumType.ORDINAL)
    public TypeVehicle getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(TypeVehicle typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public Integer getHeath() {
        return heath;
    }

    public void setHeath(Integer heath) {
        this.heath = heath;
    }

    @Override
    public void dead() {
        this.getGarage().removeVehicle(this);
    }
}
