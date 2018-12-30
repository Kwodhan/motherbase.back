package com.motherbase.apirest.model.motherbase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.staff.vehicule.TypeVehicle;
import com.motherbase.apirest.model.staff.vehicule.Vehicle;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Garage {
    private Long id;
    private MotherBase motherBase;
    private Set<Vehicle> vehicles;

    public Garage() {
        this.vehicles = new HashSet<>();
        this.addVehicle(new Vehicle(TypeVehicle.Reconnaissance));
    }


    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        vehicle.setGarage(this);
    }

    public void removeVehicle(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
        vehicle.setGarage(null);

    }

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public MotherBase getMotherBase() {
        return motherBase;
    }

    public void setMotherBase(MotherBase motherBase) {
        this.motherBase = motherBase;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
