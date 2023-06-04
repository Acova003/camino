package com.ameec.camino.services;

import java.util.List;
import com.ameec.camino.dtos.UserDto;

public interface UserService{

    List<String> addUser(UserDto userDto);

    List<String> userLogin(UserDto userDto, String password);

    List<String> subscribe(String email, Long subscriberId);

    UserDto getUserById(Long userId);

    List<String> updateSteps(Long userId, Long newSteps);

}