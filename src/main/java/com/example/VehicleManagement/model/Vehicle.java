package com.example.VehicleManagement.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ad_vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_vehicle_id")
    private Long id;

    private String vehicleName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "ad_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ad_type_vehicle_id")
    private TypeVehicle typeVehicle;

    private String brand;
    private String model;
    private String style;
    private String licensePlate;
    private int year;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Column(name = "isactive")
    private boolean isActive;

    @OneToMany(mappedBy = "vehicle")
    private List<MaintenanceVehicle> maintenanceRecords;

    public Vehicle() {
    }

    public Vehicle(Long id, String vehicleName, String description, User user, TypeVehicle typeVehicle, String brand,
            String model, String style, String licensePlate, int year, Timestamp createdAt, Timestamp updatedAt,
            boolean isActive, List<MaintenanceVehicle> maintenanceRecords) {
        this.id = id;
        this.vehicleName = vehicleName;
        this.description = description;
        this.user = user;
        this.typeVehicle = typeVehicle;
        this.brand = brand;
        this.model = model;
        this.style = style;
        this.licensePlate = licensePlate;
        this.year = year;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.maintenanceRecords = maintenanceRecords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TypeVehicle getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(TypeVehicle typeVehicle) {
        this.typeVehicle = typeVehicle;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<MaintenanceVehicle> getMaintenanceRecords() {
        return maintenanceRecords;
    }

    public void setMaintenanceRecords(List<MaintenanceVehicle> maintenanceRecords) {
        this.maintenanceRecords = maintenanceRecords;
    }
}
