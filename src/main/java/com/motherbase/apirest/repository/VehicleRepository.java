package com.motherbase.apirest.repository;

import com.motherbase.apirest.model.staff.vehicule.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


}
