package com.example.VehicleManagement.dto;

import java.sql.Timestamp;
import java.util.List;

public class VehicleReponse {

    private Long vehicleId;
    private String vehicleName;
    private String description;

    private String user;

    private String type;

    private String brand;
    private String model;
    private String style;
    private String licensePlate;
    private int year;

    private Timestamp createdAt;
    private Timestamp updatedAt;


    private List<MaintenanceVehicleReponse> maintenanceVehicleReponseList;

    public VehicleReponse() {
    }

    public VehicleReponse(Long vehicleId, String vehicleName, String description, String user, String type,
            String brand, String model,
            String style, String licensePlate, int year, Timestamp createdAt, Timestamp updatedAt,
            List<MaintenanceVehicleReponse> maintenanceVehicleReponseList) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.description = description;
        this.user = user;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.style = style;
        this.licensePlate = licensePlate;
        this.year = year;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.maintenanceVehicleReponseList = maintenanceVehicleReponseList;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public List<MaintenanceVehicleReponse> getMaintenanceVehicleReponseList() {
        return maintenanceVehicleReponseList;
    }

    public void setMaintenanceVehicleReponseList(
            List<MaintenanceVehicleReponse> maintenanceVehicleReponseList) {
        this.maintenanceVehicleReponseList = maintenanceVehicleReponseList;
    }
}
