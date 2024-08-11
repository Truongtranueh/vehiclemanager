package com.example.VehicleManagement.dto;

import java.sql.Timestamp;

public class MaintenanceVehicleReponse {

    private Long maintenanceVehicleId;
    private String nameMaintenance;
    private Long vehicleId;
    private String description;
    private double cost;
    private double discount;
    private double finalCost;
    private Timestamp serviceDate;

    private Timestamp createdAt;

    private Timestamp updateAt;

    private boolean isActive;

    public MaintenanceVehicleReponse() {
    }

    public MaintenanceVehicleReponse(Long maintenanceVehicleId, String nameMaintenance, Long vehicleId,
            String description, double cost, double discount, double finalCost, Timestamp serviceDate,
            Timestamp createdAt,
            Timestamp updateAt, boolean isActive) {
        this.maintenanceVehicleId = maintenanceVehicleId;
        this.nameMaintenance = nameMaintenance;
        this.vehicleId = vehicleId;
        this.description = description;
        this.cost = cost;
        this.discount = discount;
        this.finalCost = finalCost;
        this.serviceDate = serviceDate;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.isActive = isActive;
    }

    public Long getMaintenanceVehicleId() {
        return maintenanceVehicleId;
    }

    public void setMaintenanceVehicleId(Long maintenanceVehicleId) {
        this.maintenanceVehicleId = maintenanceVehicleId;
    }

    public String getNameMaintenance() {
        return nameMaintenance;
    }

    public void setNameMaintenance(String nameMaintenance) {
        this.nameMaintenance = nameMaintenance;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
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

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
