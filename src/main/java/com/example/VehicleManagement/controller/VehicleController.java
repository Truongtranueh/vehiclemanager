package com.example.VehicleManagement.controller;

import com.example.VehicleManagement.Constant.ReponseConstant;
import com.example.VehicleManagement.Constant.UserConstant;
import com.example.VehicleManagement.config.JwtTokenUtil;
import com.example.VehicleManagement.dto.ApiReponse;
import com.example.VehicleManagement.dto.MaintenanceVehicleReponse;
import com.example.VehicleManagement.dto.VehicleRequest;
import com.example.VehicleManagement.dto.VehicleReponse;
import com.example.VehicleManagement.model.MaintenanceVehicle;
import com.example.VehicleManagement.model.TypeVehicle;
import com.example.VehicleManagement.model.User;
import com.example.VehicleManagement.model.Vehicle;
import com.example.VehicleManagement.services.MaintenanceVehicleService;
import com.example.VehicleManagement.services.TypeVehicleService;
import com.example.VehicleManagement.services.UserService;
import com.example.VehicleManagement.services.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    @Autowired
    private TypeVehicleService typeVehicleService;

    @Autowired
    private MaintenanceVehicleService maintenanceVehicleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/add")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ApiReponse> addVehicle(@RequestBody VehicleRequest vehicleRequest) {

        String validationMessage = validateVehicleRequest(vehicleRequest);

        if (validationMessage != null) {
            return badRequest(validationMessage);
        }

        try {

            User owner = userService.findById(vehicleRequest.getUserOwnerId());

            if (owner == null) {
                return badRequest("can not find owner by userOwnerId");
            }

            TypeVehicle typeVehicle = typeVehicleService.findById(vehicleRequest.getTypeVehicle());

            if (typeVehicle == null) {
                return badRequest("can not find typeVehicle");
            }

            Vehicle vehicle = buildVehicle(vehicleRequest, owner, typeVehicle);

            vehicleService.createNewVehicle(vehicle);

            return success("create vehicle success: " + vehicle.getId());

        } catch (Exception e) {
            return handleException("error while creating vehicle", e);
        }
    }

    @GetMapping("/getinfo")
    public ResponseEntity<ApiReponse> getVehicles(@RequestParam("nameVehicle") String name) {

        User currentUser = jwtTokenUtil.getCurrentUser();

        if (name == null || name.isEmpty()) {
            return badRequest("name is null or empty");
        }

        try {

            List<Vehicle> vehicles = vehicleService.findVehiclesByName(name);

            if (UserConstant.ROLE_ADMIN.equals(currentUser.getRole().getRoleName())) {

                List<VehicleReponse> vehicleReponsesList = buildVehicleReponses(vehicles,
                        currentUser.getRole().getRoleName());

                return success(vehicleReponsesList);
            }

            List<Vehicle> userVehicles = vehicles.stream()
                    .filter(vehicle -> vehicle.getUser().getId().equals(currentUser.getId()))
                    .collect(Collectors.toList());

            List<VehicleReponse> vehicleReponsesList = buildVehicleReponses(userVehicles,
                    currentUser.getRole().getRoleName());

            return success(vehicleReponsesList);

        } catch (Exception e) {
            return handleException("error while getting vehicle info", e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiReponse> updateVehicle(@RequestBody VehicleRequest vehicleRequest) {

        String validationMessage = validateVehicleRequest(vehicleRequest);

        if (validationMessage != null) {
            return badRequest(validationMessage);
        }

        try {
            User currentUser = jwtTokenUtil.getCurrentUser();

            User owner = userService.findById(vehicleRequest.getUserOwnerId());

            if (owner == null) {
                return badRequest("can not find owner by userOwnerId");
            }

            if (UserConstant.ROLE_USER.equals(currentUser.getRole().getRoleName())) {

                if (owner.getId() != currentUser.getId()) {
                    return badRequest("You are not authorized to update this vehicle");
                }
            }

            TypeVehicle typeVehicle = typeVehicleService.findById(vehicleRequest.getTypeVehicle());

            if (typeVehicle == null) {
                return badRequest("can not find typeVehicle");
            }

            Vehicle vehicle = buildVehicle(vehicleRequest, owner, typeVehicle);

            Vehicle updatedVehicle = vehicleService.updateVehicle(vehicle);

            return success("Update vehicle success: " + updatedVehicle.getVehicleName());

        } catch (Exception e) {
            return handleException("error while updating vehicle", e);
        }
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ApiReponse> deleteVehicle(@PathVariable Long id) {

        if (id == null) {
            return badRequest("id is null");
        }

        try {

            vehicleService.deleteVehicle(id);

            return success("delete vehicle success: " + id);

        } catch (Exception e) {
            return handleException("error while deleting vehicle", e);
        }
    }

    private String validateVehicleRequest(VehicleRequest vehicleRequest) {

        if (vehicleRequest.getName() == null || vehicleRequest.getName().isEmpty()) {
            return "name is null or empty";
        }

        if (vehicleRequest.getUserOwnerId() == null) {
            return "userOwner is null";
        }

        if (vehicleRequest.getTypeVehicle() == null) {
            return "typeVehicle is null";
        }
        return null;
    }

    private Vehicle buildVehicle(VehicleRequest vehicleRequest, User owner, TypeVehicle typeVehicle) {

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleName(vehicleRequest.getName());
        vehicle.setDescription(vehicleRequest.getDescription());
        vehicle.setBrand(vehicleRequest.getBranch());

        vehicle.setModel(vehicleRequest.getModel());
        vehicle.setStyle(vehicleRequest.getStyle());
        vehicle.setTypeVehicle(typeVehicle);

        vehicle.setUser(owner);
        vehicle.setLicensePlate(vehicleRequest.getLicense_plate());
        vehicle.setYear(vehicleRequest.getYear());

        vehicle.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        vehicle.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        vehicle.setActive(true);

        return vehicle;
    }

    private List<VehicleReponse> buildVehicleReponses(List<Vehicle> vehicles, String role) {

        List<VehicleReponse> vehicleReponsesList = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {

            VehicleReponse vehicleReponse = new VehicleReponse();
            vehicleReponse.setVehicleId(vehicle.getId());
            vehicleReponse.setVehicleName(vehicle.getVehicleName());

            vehicleReponse.setDescription(vehicle.getDescription());
            vehicleReponse.setUser(vehicle.getUser().getUsername());
            vehicleReponse.setType(vehicle.getTypeVehicle().getName());

            vehicleReponse.setBrand(vehicle.getBrand());
            vehicleReponse.setModel(vehicle.getModel());
            vehicleReponse.setStyle(vehicle.getStyle());

            vehicleReponse.setLicensePlate(vehicle.getLicensePlate());
            vehicleReponse.setYear(vehicle.getYear());
            vehicleReponse.setCreatedAt(vehicle.getCreatedAt());

            vehicleReponse.setUpdatedAt(vehicle.getUpdatedAt());

            List<MaintenanceVehicle> maintenanceVehicleList = this.maintenanceVehicleService.findMaintenanceVehicleByVehicle(
                    vehicle);

            if (UserConstant.ROLE_ADMIN.equals(maintenanceVehicleList)) {

                List<MaintenanceVehicleReponse> maintenanceVehicleReponses = maintenanceVehicleList.stream()
                        .map(this::convertToMaintenanceVehicleReponse)
                        .collect(Collectors.toList());

                vehicleReponse.setMaintenanceVehicleReponseList(maintenanceVehicleReponses);
            }

            vehicleReponsesList.add(vehicleReponse);

        }
        return vehicleReponsesList;
    }

    private MaintenanceVehicleReponse convertToMaintenanceVehicleReponse(MaintenanceVehicle maintenanceVehicle) {

        MaintenanceVehicleReponse response = new MaintenanceVehicleReponse();
        response.setMaintenanceVehicleId(maintenanceVehicle.getId());
        response.setNameMaintenance(maintenanceVehicle.getNameMaintenance());

        response.setVehicleId(maintenanceVehicle.getVehicle() != null ? maintenanceVehicle.getVehicle().getId() : null);

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

    private ResponseEntity<ApiReponse> badRequest(String message) {

        ApiReponse response = new ApiReponse(ReponseConstant.FAIL, message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ResponseEntity<ApiReponse> success(Object data) {

        ApiReponse response = new ApiReponse(ReponseConstant.SUCCESS, data);

        return ResponseEntity.ok(response);
    }

    private ResponseEntity<ApiReponse> handleException(String message, Exception e) {

        logger.error(message, e);

        ApiReponse response = new ApiReponse(ReponseConstant.FAIL, message + " fail");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

