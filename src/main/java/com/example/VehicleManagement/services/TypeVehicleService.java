package com.example.VehicleManagement.services;

import com.example.VehicleManagement.model.Role;
import com.example.VehicleManagement.model.TypeVehicle;
import com.example.VehicleManagement.model.Vehicle;
import com.example.VehicleManagement.repository.TypeVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeVehicleService {

    @Autowired
    private TypeVehicleRepository typeVehicleRepository;

    public TypeVehicle createNewTypeVehicle(TypeVehicle typeVehicle) {
        return typeVehicleRepository.save(typeVehicle);
    }

    public TypeVehicle findByName(String name) {

        return typeVehicleRepository.findByName(name);
    }

    public TypeVehicle findById(Long id) {
        Optional<TypeVehicle> typeVehicle = typeVehicleRepository.findById(id);

        return typeVehicle.orElse(null);
    }
}
