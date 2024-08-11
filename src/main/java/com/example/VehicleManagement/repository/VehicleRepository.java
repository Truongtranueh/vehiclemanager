package com.example.VehicleManagement.repository;

import com.example.VehicleManagement.model.User;
import com.example.VehicleManagement.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUser(User user);

    List<Vehicle> findByVehicleName(String name);

}
