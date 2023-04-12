package com.ameec.camino.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ameec.camino.repositories.UserRepository;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;
}
