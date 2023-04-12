package com.ameec.camino.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ameec.camino.dtos.UserDto;
import com.ameec.camino.entities.User;
import com.ameec.camino.repositories.UserRepository;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public List<String> addUser(UserDto userDto) {
        List<String> response = new ArrayList<>();
        User user = new User(userDto);
        userRepository.saveAndFlush(user);
        response.add("User added successfully");
        return response;
    }
}
