package com.example.VehicleManagement.repository;

import com.example.VehicleManagement.model.TypeVehicle;
import com.example.VehicleManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeVehicleRepository extends JpaRepository<TypeVehicle, Long> {

    TypeVehicle findByName(String name);
}
