package com.ameec.camino.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ameec.camino.entities.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    
}
