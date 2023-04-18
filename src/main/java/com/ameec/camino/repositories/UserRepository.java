package com.ameec.camino.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ameec.camino.dtos.UserDto;
import com.ameec.camino.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    List<String> addUser(UserDto userDto);

    List<String> userLogin(UserDto userDto);
    
    Optional<User> findByEmail(String email);
    
}
