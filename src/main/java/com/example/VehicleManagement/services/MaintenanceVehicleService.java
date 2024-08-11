package com.example.VehicleManagement.services;

import com.example.VehicleManagement.model.MaintenanceVehicle;
import com.example.VehicleManagement.model.Vehicle;
import com.example.VehicleManagement.repository.MaintenanceVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceVehicleService {
    @Autowired
    private MaintenanceVehicleRepository maintenanceVehicleRepository;

    public MaintenanceVehicle addMaintenanceVehicle(MaintenanceVehicle maintenanceVehicle) {
        return maintenanceVehicleRepository.save(maintenanceVehicle);
    }

    public MaintenanceVehicle findMaintenanceVehicleById(Long id) {

        Optional<MaintenanceVehicle> maintenanceVehicle =  maintenanceVehicleRepository.findById(id);
        return maintenanceVehicle.orElse(null);
    }
    public List<MaintenanceVehicle> findMaintenanceVehicleByVehicle(Vehicle vehicle) {

        List<MaintenanceVehicle> maintenanceVehicleList = maintenanceVehicleRepository.findByVehicle(vehicle);
        return maintenanceVehicleList;
    }

    public MaintenanceVehicle updateMaintenanceVehicle(MaintenanceVehicle maintenanceVehicle) {
        return maintenanceVehicleRepository.save(maintenanceVehicle);
    }
}
