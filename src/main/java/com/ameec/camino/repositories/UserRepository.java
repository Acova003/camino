package com.ameec.camino.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ameec.camino.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
