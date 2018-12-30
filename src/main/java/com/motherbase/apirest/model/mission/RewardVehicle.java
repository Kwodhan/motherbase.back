package com.motherbase.apirest.model.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.staff.vehicule.TypeVehicle;

import javax.persistence.*;

@Entity
@Table(name = "MISSION_REWARD_VEHICLE")
public class RewardVehicle {
    private Long id;
    private TypeVehicle typeVehicle;
    private Mission mission;

    public RewardVehicle(TypeVehicle typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public RewardVehicle() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeVehicle getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(TypeVehicle typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    @ManyToOne
    @JsonIgnore
    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }
}
