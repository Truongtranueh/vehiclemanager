package com.example.VehicleManagement.services;

import com.example.VehicleManagement.model.User;
import com.example.VehicleManagement.model.Vehicle;
import com.example.VehicleManagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    public Vehicle createNewVehicle(Vehicle vehicle){
        return  vehicleRepository.save(vehicle);
    }

    public List<Vehicle> findVehiclesByUser(User user) {
        return vehicleRepository.findByUser(user);
    }

    public List<Vehicle> findVehiclesByName(String name) {
        return vehicleRepository.findByVehicleName(name);
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public Vehicle findById(Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        return vehicle.orElse(null);
    }
}
