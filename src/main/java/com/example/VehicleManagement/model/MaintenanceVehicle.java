package com.example.VehicleManagement.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "ad_maintenance_vehicle")
public class MaintenanceVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_maintenance_vehicle_id")
    private Long id;

    private String nameMaintenance;

    @ManyToOne
    @JoinColumn(name = "ad_vehicle_id")
    private Vehicle vehicle;

    private String description;
    private double cost;
    private double discount;
    private double finalCost;
    private Timestamp serviceDate;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isActive;

    @OneToMany(mappedBy = "maintenanceVehicle")
    private List<MaintenanceCostDetail> costDetails;

    public MaintenanceVehicle() {
    }

    public MaintenanceVehicle(Long id, String nameMaintenance, Vehicle vehicle, String description, double cost,
            double discount, double finalCost, Timestamp serviceDate, Timestamp createdAt, Timestamp updatedAt,
            boolean isActive, List<MaintenanceCostDetail> costDetails) {
        this.id = id;
        this.nameMaintenance = nameMaintenance;
        this.vehicle = vehicle;
        this.description = description;
        this.cost = cost;
        this.discount = discount;
        this.finalCost = finalCost;
        this.serviceDate = serviceDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.costDetails = costDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameMaintenance() {
        return nameMaintenance;
    }

    public void setNameMaintenance(String nameMaintenance) {
        this.nameMaintenance = nameMaintenance;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }

    public Timestamp getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Timestamp serviceDate) {
        this.serviceDate = serviceDate;
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

    public List<MaintenanceCostDetail> getCostDetails() {
        return costDetails;
    }

    public void setCostDetails(List<MaintenanceCostDetail> costDetails) {
        this.costDetails = costDetails;
    }
}
