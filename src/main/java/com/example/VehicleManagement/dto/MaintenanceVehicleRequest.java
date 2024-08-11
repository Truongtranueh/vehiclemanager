package com.example.VehicleManagement.dto;

import java.sql.Timestamp;

public class MaintenanceVehicleRequest {

    private Long id;
    private String nameMaintenance;
    private Long vehicleId;
    private String description;
    private double cost;
    private double discount;
    private double finalCost;
    private Timestamp serviceDate;

    public MaintenanceVehicleRequest() {
    }

    public MaintenanceVehicleRequest(Long id, String nameMaintenance, Long vehicleId, String description, double cost,
            double discount, double finalCost, Timestamp serviceDate) {
        this.id = id;
        this.nameMaintenance = nameMaintenance;
        this.vehicleId = vehicleId;
        this.description = description;
        this.cost = cost;
        this.discount = discount;
        this.finalCost = finalCost;
        this.serviceDate = serviceDate;
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

    public void setVehicleName(Long vehicleId) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
