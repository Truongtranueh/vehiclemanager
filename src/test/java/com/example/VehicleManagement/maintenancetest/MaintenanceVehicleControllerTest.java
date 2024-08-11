package com.example.VehicleManagement.maintenancetest;

import com.example.VehicleManagement.Constant.UserConstant;
import com.example.VehicleManagement.config.JwtTokenUtil;
import com.example.VehicleManagement.controller.MaintenanceVehicleController;
import com.example.VehicleManagement.dto.ApiReponse;
import com.example.VehicleManagement.dto.MaintenanceVehicleReponse;
import com.example.VehicleManagement.dto.MaintenanceVehicleRequest;
import com.example.VehicleManagement.model.MaintenanceVehicle;
import com.example.VehicleManagement.model.Role;
import com.example.VehicleManagement.model.User;
import com.example.VehicleManagement.model.Vehicle;
import com.example.VehicleManagement.services.MaintenanceVehicleService;
import com.example.VehicleManagement.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MaintenanceVehicleControllerTest {

    @InjectMocks
    private MaintenanceVehicleController maintenanceVehicleController;

    @Mock
    private MaintenanceVehicleService maintenanceVehicleService;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = "user")
    public void testAddMaintenanceRecord_Success() {

        MaintenanceVehicleRequest request = new MaintenanceVehicleRequest();

        request.setNameMaintenance("Oil Change");
        request.setVehicleId(1L);
        request.setDescription("Changed engine oil");

        request.setCost(100.0);
        request.setDiscount(10.0);
        request.setFinalCost(90.0);

        User currentUser = new User();

        Role role = new Role();

        role.setId(2L);
        role.setRoleName(UserConstant.ROLE_USER);

        currentUser.setId(1L);
        currentUser.setRole(role);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setUser(currentUser);

        MaintenanceVehicle maintenanceVehicle = new MaintenanceVehicle();

        maintenanceVehicle.setId(1L);
        maintenanceVehicle.setNameMaintenance("Oil Change");

        when(jwtTokenUtil.getCurrentUser()).thenReturn(currentUser);
        when(vehicleService.findById(request.getVehicleId())).thenReturn(vehicle);
        when(maintenanceVehicleService.addMaintenanceVehicle(any(MaintenanceVehicle.class))).thenReturn(
                maintenanceVehicle);

        ResponseEntity<ApiReponse> response = maintenanceVehicleController.addMaintenanceRecord(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Add maintenance vehicle success: 1", response.getBody().getMessage());
        verify(maintenanceVehicleService, times(1)).addMaintenanceVehicle(any(MaintenanceVehicle.class));
    }

    @Test
    @WithMockUser(roles = "user")
    public void testAddMaintenanceRecord_AuthorizationFailure() {

        MaintenanceVehicleRequest request = new MaintenanceVehicleRequest();
        request.setNameMaintenance("Oil Change");
        request.setVehicleId(1L);
        request.setDescription("Changed engine oil");
        request.setCost(100.0);
        request.setDiscount(10.0);
        request.setFinalCost(90.0);

        User currentUser = new User();

        Role role = new Role();

        role.setId(2L);
        role.setRoleName(UserConstant.ROLE_USER);

        currentUser.setId(2L);
        currentUser.setRole(role);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setUser(new User());
        vehicle.getUser().setId(1L);

        when(jwtTokenUtil.getCurrentUser()).thenReturn(currentUser);
        when(vehicleService.findById(request.getVehicleId())).thenReturn(vehicle);

        ResponseEntity<ApiReponse> response = maintenanceVehicleController.addMaintenanceRecord(request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("You are not authorized to add maintenance to this vehicle", response.getBody().getMessage());
        verify(maintenanceVehicleService, never()).addMaintenanceVehicle(any(MaintenanceVehicle.class));
    }

    @Test
    public void testGetMaintenanceVehicle_Success() {

        Long vehicleId = 1L;

        User currentUser = new User();

        Role role = new Role();

        role.setId(2L);
        role.setRoleName(UserConstant.ROLE_USER);

        currentUser.setId(1L);
        currentUser.setRole(role);

        MaintenanceVehicle maintenanceVehicle = new MaintenanceVehicle();

        maintenanceVehicle.setId(1L);
        maintenanceVehicle.setVehicle(new Vehicle());
        maintenanceVehicle.getVehicle().setUser(new User());

        maintenanceVehicle.getVehicle().getUser().setId(1L);
        MaintenanceVehicleReponse responseDto = new MaintenanceVehicleReponse();
        responseDto.setMaintenanceVehicleId(1L);

        when(jwtTokenUtil.getCurrentUser()).thenReturn(currentUser);
        when(maintenanceVehicleService.findMaintenanceVehicleById(vehicleId)).thenReturn(maintenanceVehicle);
        when(maintenanceVehicleController.buildMaintenanceVehicleResponse(maintenanceVehicle)).thenReturn(responseDto);

        ResponseEntity<ApiReponse> response = maintenanceVehicleController.getMaintenanceVehicle(vehicleId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody().getData());
        verify(maintenanceVehicleService, times(1)).findMaintenanceVehicleById(vehicleId);
    }

    @Test
    public void testUpdateMaintenanceVehicle_Success() {

        MaintenanceVehicleRequest request = new MaintenanceVehicleRequest();

        request.setVehicleId(1L);
        request.setNameMaintenance("Oil Change");
        request.setDescription("Changed engine oil");

        request.setCost(100.0);
        request.setDiscount(10.0);
        request.setFinalCost(90.0);

        User currentUser = new User();

        Role role = new Role();
        role.setId(2L);
        role.setRoleName(UserConstant.ROLE_USER);

        currentUser.setId(1L);
        currentUser.setRole(role);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setUser(currentUser);

        MaintenanceVehicle existingMaintenanceVehicle = new MaintenanceVehicle();
        existingMaintenanceVehicle.setId(1L);

        MaintenanceVehicle updatedMaintenanceVehicle = new MaintenanceVehicle();
        updatedMaintenanceVehicle.setId(1L);

        when(jwtTokenUtil.getCurrentUser()).thenReturn(currentUser);
        when(vehicleService.findById(request.getVehicleId())).thenReturn(vehicle);
        when(maintenanceVehicleService.findMaintenanceVehicleById(request.getVehicleId())).thenReturn(
                existingMaintenanceVehicle);
        when(maintenanceVehicleService.updateMaintenanceVehicle(existingMaintenanceVehicle)).thenReturn(
                updatedMaintenanceVehicle);

        ResponseEntity<ApiReponse> response = maintenanceVehicleController.updateMaintenanceVehicle(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Update maintenance vehicle success: 1", response.getBody().getMessage());
        verify(maintenanceVehicleService, times(1)).updateMaintenanceVehicle(existingMaintenanceVehicle);
    }

    @Test
    public void testUpdateMaintenanceVehicle_NotFound() {
        
        MaintenanceVehicleRequest request = new MaintenanceVehicleRequest();
        request.setId(1L);

        when(vehicleService.findById(request.getVehicleId())).thenReturn(null);

        ResponseEntity<ApiReponse> response = maintenanceVehicleController.updateMaintenanceVehicle(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cannot find vehicle by vehicleId", response.getBody().getMessage());
        verify(maintenanceVehicleService, never()).updateMaintenanceVehicle(any(MaintenanceVehicle.class));
    }
}
