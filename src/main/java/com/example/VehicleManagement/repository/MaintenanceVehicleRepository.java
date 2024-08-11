package com.example.VehicleManagement.repository;

import com.example.VehicleManagement.model.MaintenanceVehicle;
import com.example.VehicleManagement.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceVehicleRepository  extends JpaRepository<MaintenanceVehicle, Long> {
    List<MaintenanceVehicle> findByVehicle(Vehicle vehicle);
}
