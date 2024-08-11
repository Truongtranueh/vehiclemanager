package com.example.VehicleManagement.services;

import com.example.VehicleManagement.model.Role;
import com.example.VehicleManagement.model.User;
import com.example.VehicleManagement.repository.RoleRepository;
import com.example.VehicleManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User createNewUser(User user){
        return userRepository.save(user);
    }

    public User findByUserName (String userName){
        return userRepository.findByUsername(userName);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    public User findById(Long id) {

        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public Role findRoleById(Long id) {

        Optional<Role> role = roleRepository.findById(id);

        return role.orElse(null);
    }

}
