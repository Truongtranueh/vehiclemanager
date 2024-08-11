package com.example.VehicleManagement.usertest;

import com.example.VehicleManagement.config.JwtTokenUtil;
import com.example.VehicleManagement.controller.UserController;
import com.example.VehicleManagement.dto.ApiReponse;
import com.example.VehicleManagement.dto.AuthenticationRequest;
import com.example.VehicleManagement.dto.AuthenticationResponse;
import com.example.VehicleManagement.dto.RegisterUserRequest;
import com.example.VehicleManagement.model.Role;
import com.example.VehicleManagement.model.User;
import com.example.VehicleManagement.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {

        RegisterUserRequest request = new RegisterUserRequest();

        request.setUsername("truongtranbmt3");
        request.setPassword("password123");
        request.setRoleId(2L);

        Role role = new Role();
        role.setId(1L);
        role.setRoleName("user");

        when(userService.existsByUsername(anyString())).thenReturn(false);
        when(userService.findRoleById(anyLong())).thenReturn(role);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userService.createNewUser(any(User.class))).thenReturn(new User());

        ResponseEntity<ApiReponse> response = userController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("create user success: truongtranbmt3", response.getBody().getData());

        verify(userService).existsByUsername("testuser");
        verify(userService).findRoleById(1L);
        verify(userService).createNewUser(any(User.class));
    }

    @Test
    public void testRegister_UserExists() {

        RegisterUserRequest request = new RegisterUserRequest();

        request.setUsername("usertruong");
        request.setPassword("dddd233333");
        request.setRoleId(2L);

        when(userService.existsByUsername(anyString())).thenReturn(true);

        ResponseEntity<ApiReponse> response = userController.register(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("user already exists", response.getBody().getData());

        verify(userService).existsByUsername("usertruong");
        verify(userService, never()).createNewUser(any(User.class));
    }

    @Test
    public void testLogin_Success() {

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("usertruong");
        request.setPassword("123");

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("dummyToken");

        ResponseEntity<?> response = userController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("dummyToken", ((AuthenticationResponse) response.getBody()).getJwt());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenUtil).generateToken(userDetails);
    }

    @Test
    public void testLogin_Failure() {

        AuthenticationRequest request = new AuthenticationRequest();

        request.setUsername("truongf");
        request.setPassword("passssf");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        ResponseEntity<?> response = userController.login(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(((ApiReponse) response.getBody()).getMessage().contains("fail"));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenUtil, never()).generateToken(any(UserDetails.class));
    }
}
