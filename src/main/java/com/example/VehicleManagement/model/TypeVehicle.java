package com.example.VehicleManagement.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "ad_type_vehicle")
public class TypeVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_type_vehicle_id")
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "typeVehicle")
    private List<Vehicle> vehicles;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Column(name = "isactive")
    private boolean isActive;

    public TypeVehicle() {
    }

    public TypeVehicle(Long id, String name, String description, List<Vehicle> vehicles, Timestamp createdAt,
            Timestamp updatedAt, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.vehicles = vehicles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
