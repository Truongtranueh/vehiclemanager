package com.example.VehicleManagement.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "ad_maintenance_cost_detail")
public class MaintenanceCostDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_maintenance_cost_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ad_maintenance_vehicle_id")
    private MaintenanceVehicle maintenanceVehicle;

    private String description;
    private double cost;
    private double discount;
    private double finalCost;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isActive;

    public MaintenanceCostDetail() {

    }

    public MaintenanceCostDetail(Long id, MaintenanceVehicle maintenanceVehicle, String description, double cost,
            double discount, double finalCost, Timestamp createdAt, Timestamp updatedAt, boolean isActive) {
        this.id = id;
        this.maintenanceVehicle = maintenanceVehicle;
        this.description = description;
        this.cost = cost;
        this.discount = discount;
        this.finalCost = finalCost;
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

    public MaintenanceVehicle getMaintenanceVehicle() {
        return maintenanceVehicle;
    }

    public void setMaintenanceVehicle(MaintenanceVehicle maintenanceVehicle) {
        this.maintenanceVehicle = maintenanceVehicle;
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