package com.motherbase.apirest.model.staff.vehicule;

public enum TypeVehicle {
    Tank(10),
    Reconnaissance(8);

    private Integer force;

    TypeVehicle(Integer force) {
        this.force = force;
    }

    public Integer getForce() {
        return force;
    }


}
