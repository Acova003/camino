package com.ameec.camino.services;

import java.util.List;
import java.util.Optional;

import com.ameec.camino.dtos.UserDto;
import com.ameec.camino.entities.User;

public interface UserService {

    List<String> addUser(UserDto userDto);

    List<String> userLogin(UserDto userDto);

    Optional<User> findByEmail(String email);

}