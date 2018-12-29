package com.motherbase.apirest.service;

import com.motherbase.apirest.model.staff.vehicule.Vehicle;

public interface VehicleService {
    Vehicle create(Vehicle vehicle);


    Vehicle findById(Long id);
}
