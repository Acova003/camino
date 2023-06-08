package com.ameec.camino.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ameec.camino.dtos.UserDto;
import com.ameec.camino.entities.Subscription;
import com.ameec.camino.services.UserService;
import com.ameec.camino.services.SubscriberService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SubscriberService subscriberService;

    @PostMapping("/register")
    public List<String> addUser(@RequestBody UserDto userDto){
        String passHash = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(passHash);
        userDto.setSteps(0L);
        return userService.addUser(userDto);
    }

    @PostMapping("/login")
    public List<String> userLogin(@RequestBody UserDto userDto){
        String passHash = passwordEncoder.encode(userDto.getPassword());
        return userService.userLogin(userDto, passHash);
    }
    // @RequestBody saying create this variable based on the JSON request that just came in

    @PostMapping("/subscribe/{subscribeeEmail}/{subscriberId}")
    public void subscribe(@PathVariable String subscribeeEmail, @PathVariable Long subscriberId){
        subscriberService.subscribe(subscribeeEmail, subscriberId);
    }  
      
    // get all subscribees
    @GetMapping("/subscribees/{subscriberId}")
    public List<Subscription> getSubscribees(@PathVariable Long subscriberId){
        return subscriberService.getSubscriptions(subscriberId);
    }


    @GetMapping("/{userId}") 
    public UserDto getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/updateSteps/{userId}/{newSteps}")
    public List<String> updateSteps(@PathVariable Long userId, @PathVariable Long newSteps) {
        return userService.updateSteps(userId, newSteps);
    }
    
}

