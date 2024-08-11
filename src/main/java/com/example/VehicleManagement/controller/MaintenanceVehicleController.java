package com.example.VehicleManagement.controller;

import com.example.VehicleManagement.Constant.ReponseConstant;
import com.example.VehicleManagement.Constant.UserConstant;
import com.example.VehicleManagement.config.JwtTokenUtil;
import com.example.VehicleManagement.dto.ApiReponse;
import com.example.VehicleManagement.dto.MaintenanceVehicleReponse;
import com.example.VehicleManagement.dto.MaintenanceVehicleRequest;
import com.example.VehicleManagement.model.MaintenanceVehicle;
import com.example.VehicleManagement.model.User;
import com.example.VehicleManagement.model.Vehicle;
import com.example.VehicleManagement.services.MaintenanceVehicleService;
import com.example.VehicleManagement.services.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceVehicleController {

    private static final Logger logger = LoggerFactory.getLogger(MaintenanceVehicleController.class);

    @Autowired
    private MaintenanceVehicleService maintenanceVehicleService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/add")
    public ResponseEntity<ApiReponse> addMaintenanceRecord(@RequestBody MaintenanceVehicleRequest request) {

        User currentUser = jwtTokenUtil.getCurrentUser();

        String validationMessage = validateMaintenanceRequest(request);

        if (validationMessage != null) {

            return buildResponse(ReponseConstant.FAIL, validationMessage, HttpStatus.BAD_REQUEST);
        }

        try {

            Vehicle vehicle = vehicleService.findById(request.getVehicleId());

            if (vehicle == null) {
                return buildResponse(ReponseConstant.FAIL, "Cannot find vehicle by vehicleId", HttpStatus.BAD_REQUEST);
            }

            if (UserConstant.ROLE_USER.equals(currentUser.getRole().getRoleName()) && !currentUser.getId()
                    .equals(vehicle.getUser().getId())) {
                return buildResponse(ReponseConstant.FAIL, "You are not authorized to add maintenance to this vehicle",
                        HttpStatus.FORBIDDEN);
            }

            MaintenanceVehicle maintenanceVehicle = buildMaintenanceVehicle(request, vehicle);

            MaintenanceVehicle savedVehicle = maintenanceVehicleService.addMaintenanceVehicle(maintenanceVehicle);

            return buildResponse(ReponseConstant.SUCCESS, "Add maintenance vehicle success: " + savedVehicle.getId(),
                    HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error while adding maintenance vehicle", e);

            return buildResponse(ReponseConstant.FAIL, "Error while adding maintenance vehicle",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getinfo")
    public ResponseEntity<ApiReponse> getMaintenanceVehicle(@RequestParam("vehicleId") Long vehicleId) {

        if (vehicleId == null) {
            return buildResponse(ReponseConstant.FAIL, "vehicleId is null", HttpStatus.BAD_REQUEST);
        }

        try {

            User currentUser = jwtTokenUtil.getCurrentUser();

            MaintenanceVehicle maintenanceVehicle = maintenanceVehicleService.findMaintenanceVehicleById(vehicleId);

            if (maintenanceVehicle == null) {
                return buildResponse(ReponseConstant.FAIL, "Cannot find maintenance vehicle by vehicleId",
                        HttpStatus.BAD_REQUEST);
            }

            if (UserConstant.ROLE_USER.equals(currentUser.getRole().getRoleName()) && !currentUser.getId()
                    .equals(maintenanceVehicle.getVehicle().getUser().getId())) {

                return buildResponse(ReponseConstant.FAIL, "You are not authorized to view this maintenance record",
                        HttpStatus.FORBIDDEN);
            }

            return buildResponse(ReponseConstant.SUCCESS, buildMaintenanceVehicleResponse(maintenanceVehicle),
                    HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error while getting maintenance vehicle info", e);

            return buildResponse(ReponseConstant.FAIL, "Error while getting maintenance vehicle info",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiReponse> updateMaintenanceVehicle(
            @RequestBody MaintenanceVehicleRequest maintenanceVehicleRequest) {

        if (maintenanceVehicleRequest == null) {
            return buildResponse(ReponseConstant.FAIL, "maintenanceVehicle update is null", HttpStatus.BAD_REQUEST);
        }

        try {

            User currentUser = jwtTokenUtil.getCurrentUser();

            Vehicle vehicle = vehicleService.findById(maintenanceVehicleRequest.getVehicleId());

            if (vehicle == null) {
                return buildResponse(ReponseConstant.FAIL, "Cannot find vehicle by vehicleId", HttpStatus.BAD_REQUEST);
            }

            if (UserConstant.ROLE_USER.equals(currentUser.getRole().getRoleName()) && !currentUser.getId()
                    .equals(vehicle.getUser().getId())) {
                return buildResponse(ReponseConstant.FAIL,
                        "You are not authorized to update maintenance for this vehicle", HttpStatus.FORBIDDEN);
            }

            MaintenanceVehicle maintenanceVehicle = maintenanceVehicleService.findMaintenanceVehicleById(
                    maintenanceVehicleRequest.getVehicleId());

            if (maintenanceVehicle == null) {
                return buildResponse(ReponseConstant.FAIL, "maintenance update can not find", HttpStatus.BAD_REQUEST);
            }

            MaintenanceVehicle updatedVehicle = maintenanceVehicleService.updateMaintenanceVehicle(maintenanceVehicle);

            return buildResponse(ReponseConstant.SUCCESS,
                    "Update maintenance vehicle success: " + updatedVehicle.getId(), HttpStatus.OK);
        } catch (Exception e) {

            logger.error("Error while updating maintenance vehicle", e);
            return buildResponse(ReponseConstant.FAIL, "Error while updating maintenance vehicle",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String validateMaintenanceRequest(MaintenanceVehicleRequest request) {

        if (isNullOrEmpty(request.getNameMaintenance()))
            return "nameMaintenance is null or empty";

        if (request.getVehicleId() == null)
            return "vehicleId is null";

        return null;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public MaintenanceVehicle buildMaintenanceVehicle(MaintenanceVehicleRequest request, Vehicle vehicle) {

        MaintenanceVehicle maintenanceVehicle = new MaintenanceVehicle();

        maintenanceVehicle.setNameMaintenance(request.getNameMaintenance());
        maintenanceVehicle.setVehicle(vehicle);
        maintenanceVehicle.setDescription(request.getDescription());

        maintenanceVehicle.setCost(request.getCost());
        maintenanceVehicle.setDiscount(request.getDiscount());
        maintenanceVehicle.setFinalCost(request.getFinalCost());

        Timestamp now = new Timestamp(System.currentTimeMillis());
        maintenanceVehicle.setServiceDate(now);
        maintenanceVehicle.setCreatedAt(now);

        maintenanceVehicle.setUpdatedAt(now);
        maintenanceVehicle.setActive(true);

        return maintenanceVehicle;
    }

    public MaintenanceVehicleReponse buildMaintenanceVehicleResponse(MaintenanceVehicle maintenanceVehicle) {

        MaintenanceVehicleReponse response = new MaintenanceVehicleReponse();
        response.setMaintenanceVehicleId(maintenanceVehicle.getId());
        response.setNameMaintenance(maintenanceVehicle.getNameMaintenance());

        response.setVehicleId(maintenanceVehicle.getVehicle().getId());
        response.setDescription(maintenanceVehicle.getDescription());
        response.setCost(maintenanceVehicle.getCost());

        response.setDiscount(maintenanceVehicle.getDiscount());
        response.setFinalCost(maintenanceVehicle.getFinalCost());
        response.setServiceDate(maintenanceVehicle.getServiceDate());

        response.setCreatedAt(maintenanceVehicle.getCreatedAt());
        response.setUpdateAt(maintenanceVehicle.getUpdatedAt());
        response.setActive(maintenanceVehicle.isActive());

        return response;
    }

    private ResponseEntity<ApiReponse> buildResponse(String status, Object data, HttpStatus httpStatus) {

        ApiReponse response = new ApiReponse();

        response.setMessage(status);
        response.setData(data);

        return ResponseEntity.status(httpStatus).body(response);
    }
}
