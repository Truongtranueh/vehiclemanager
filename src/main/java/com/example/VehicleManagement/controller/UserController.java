package com.example.VehicleManagement.controller;

import com.example.VehicleManagement.Constant.ReponseConstant;
import com.example.VehicleManagement.config.JwtTokenUtil;
import com.example.VehicleManagement.dto.ApiReponse;
import com.example.VehicleManagement.dto.AuthenticationRequest;
import com.example.VehicleManagement.dto.AuthenticationResponse;
import com.example.VehicleManagement.dto.RegisterUserRequest;
import com.example.VehicleManagement.model.Role;
import com.example.VehicleManagement.model.User;
import com.example.VehicleManagement.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ApiReponse> register(@RequestBody RegisterUserRequest userRequest) {

        String validationMessage = validateRegisterRequest(userRequest);
        if (validationMessage != null) {
            return badRequest(validationMessage);
        }

        try {
            if (userService.existsByUsername(userRequest.getUsername())) {
                return badRequest("user already exists");
            }

            Role role = userService.findRoleById(userRequest.getRoleId());
            if (role == null) {
                return badRequest("user role not found");
            }

            User newUser = buildNewUser(userRequest, role);
            User createdUser = userService.createNewUser(newUser);

            return success("create user success: " + createdUser.getUsername());

        } catch (Exception e) {
            return handleException("error while creating user", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest loginRequest) {

        String validationMessage = validateLoginRequest(loginRequest);

        if (validationMessage != null) {
            return badRequest(validationMessage);
        }

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(token));

        } catch (Exception e) {
            return handleException("Error while logging in", e);
        }
    }

    private String validateRegisterRequest(RegisterUserRequest userRequest) {

        if (isNullOrEmpty(userRequest.getUsername()))
            return "Username is null or empty";

        if (isNullOrEmpty(userRequest.getPassword()))
            return "Password is null or empty";

        if (userRequest.getPassword().length() < 8)
            return "Password must be at least 8 characters";

        if (userRequest.getPassword().length() > 255)
            return "Password cannot exceed 255 characters";

        if (userRequest.getRoleId() == null)
            return "RoleId is null";

        return null;
    }

    private String validateLoginRequest(AuthenticationRequest loginRequest) {

        if (isNullOrEmpty(loginRequest.getUsername()))
            return "Username is null or empty";

        if (isNullOrEmpty(loginRequest.getPassword()))
            return "Password is null or empty";

        return null;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private User buildNewUser(RegisterUserRequest userRequest, Role role) {

        User newUser = new User();

        newUser.setUsername(userRequest.getUsername());
        newUser.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        newUser.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        newUser.isActive(true);
        newUser.setRole(role);

        return newUser;
    }

    private ResponseEntity<ApiReponse> badRequest(String message) {
        return buildResponse(ReponseConstant.FAIL, message, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiReponse> success(Object data) {
        return buildResponse(ReponseConstant.SUCCESS, data, HttpStatus.OK);
    }

    private ResponseEntity<ApiReponse> handleException(String message, Exception e) {
        logger.error(message, e);
        return buildResponse(ReponseConstant.FAIL, message + " fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiReponse> buildResponse(String status, Object data, HttpStatus httpStatus) {

        ApiReponse response = new ApiReponse();

        response.setMessage(status);
        response.setData(data);
        return ResponseEntity.status(httpStatus).body(response);
    }
}


