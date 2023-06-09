package com.ameec.camino.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ameec.camino.dtos.UserDto;
// import com.ameec.camino.entities.Trip;
import com.ameec.camino.entities.User;
// import com.ameec.camino.repositories.TripRepository;
import com.ameec.camino.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Autowired
    // private TripRepository tripRepository;

    @Override
    @Transactional
    public List<String> addUser(UserDto userDto) {
        List<String> response = new ArrayList<>();
        User user = new User(userDto);
        userRepository.saveAndFlush(user);
        response.add("http://localhost:8080/trip.html");
        return response;
    }


    @Override
    @Transactional
    public List<String> userLogin(UserDto userDto, String password) {
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByDisplayName(userDto.getDisplayName());
     
        if (userOptional.isPresent()) {
            System.out.print(password + " " + userOptional.get().getPassword());
            if (passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPassword())) {
                response.add("http://localhost:8080/trip.html");
                response.add(userOptional.get().getId().toString());
            } else {
                response.add("1: Incorrect username or password");

            }
        } else {
            response.add("2: Incorrect username or password");
        }
        return response;
    }

    @Override
    @Transactional
    public UserDto getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return new UserDto(userOptional.get());
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public List<String> updateSteps(Long id, Long steps) {
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setSteps(user.getSteps() + steps);
            userRepository.save(user);
            response.add("Steps updated");
        } else {
            response.add("User not found with id: " + id);
        }
        return response;
    }
}
