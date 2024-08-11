package com.example.VehicleManagement.vehicletest;

import com.example.VehicleManagement.config.JwtTokenUtil;
import com.example.VehicleManagement.controller.VehicleController;
import com.example.VehicleManagement.dto.ApiReponse;
import com.example.VehicleManagement.dto.VehicleRequest;
import com.example.VehicleManagement.model.TypeVehicle;
import com.example.VehicleManagement.model.User;
import com.example.VehicleManagement.model.Vehicle;
import com.example.VehicleManagement.services.MaintenanceVehicleService;
import com.example.VehicleManagement.services.TypeVehicleService;
import com.example.VehicleManagement.services.UserService;
import com.example.VehicleManagement.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private UserService userService;

    @Mock
    private TypeVehicleService typeVehicleService;

    @Mock
    private MaintenanceVehicleService maintenanceVehicleService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = "admin")
    public void testAddVehicle_Success() {

        VehicleRequest vehicleRequest = new VehicleRequest();

        vehicleRequest.setName("Xe May honda k2");
        vehicleRequest.setUserOwnerId(4L);
        vehicleRequest.setTypeVehicle(2L);

        vehicleRequest.setLicense_plate("53K4 - 2222");
        vehicleRequest.setYear(2023);

        User owner = new User();
        owner.setId(4L);
        owner.setUsername("usertruong");

        TypeVehicle typeVehicle = new TypeVehicle();
        typeVehicle.setId(2L);
        typeVehicle.setName("Car");

        Vehicle vehicle = new Vehicle();

        vehicle.setId(1L);
        vehicle.setVehicleName("Xe May honda k2");
        vehicle.setUser(owner);

        vehicle.setTypeVehicle(typeVehicle);
        vehicle.setLicensePlate("53K4 - 2222");
        vehicle.setYear(2023);

        vehicle.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        vehicle.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        vehicle.setActive(true);

        when(userService.findById(vehicleRequest.getUserOwnerId())).thenReturn(owner);
        when(typeVehicleService.findById(vehicleRequest.getTypeVehicle())).thenReturn(typeVehicle);
        when(vehicleService.createNewVehicle(any(Vehicle.class))).thenReturn(vehicle);

        ResponseEntity<ApiReponse> response = vehicleController.addVehicle(vehicleRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("create vehicle success: 1", response.getBody().getData());

        verify(vehicleService, times(1)).createNewVehicle(any(Vehicle.class));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void testAddVehicle_BadRequest() {

        VehicleRequest vehicleRequest = new VehicleRequest();

        vehicleRequest.setName("");
        vehicleRequest.setUserOwnerId(1L);
        vehicleRequest.setTypeVehicle(1L);

        ResponseEntity<ApiReponse> response = vehicleController.addVehicle(vehicleRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("name is null or empty", response.getBody().getData());

        verify(vehicleService, never()).createNewVehicle(any(Vehicle.class));
    }
}
