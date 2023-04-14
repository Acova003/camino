package com.ameec.camino.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ameec.camino.dtos.UserDto;

public interface UserService {

    List<String> addUser(UserDto userDto);

    List<String> userLogin(UserDto userDto);

}