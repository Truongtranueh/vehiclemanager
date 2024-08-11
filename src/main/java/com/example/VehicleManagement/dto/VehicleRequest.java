package com.example.VehicleManagement.dto;

public class VehicleRequest {
    private String name;
    private String description;

    private Long userOwnerId;

    private String branch;

    private Long typeVehicle;

    private String model;

    private String style;

    private String license_plate;

    private int year;

    public VehicleRequest() {
    }

    public VehicleRequest(String name, String description, Long userOwnerId, String branch, Long typeVehicle,
            String model, String style, String license_plate, int year) {
        this.name = name;
        this.description = description;
        this.userOwnerId = userOwnerId;
        this.branch = branch;
        this.typeVehicle = typeVehicle;
        this.model = model;
        this.style = style;
        this.license_plate = license_plate;
        this.year = year;
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

    public Long getUserOwnerId() {
        return userOwnerId;
    }

    public void setUserOwnerId(Long userOwnerId) {
        this.userOwnerId = userOwnerId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Long getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(Long typeVehicle) {
        this.typeVehicle = typeVehicle;
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

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
