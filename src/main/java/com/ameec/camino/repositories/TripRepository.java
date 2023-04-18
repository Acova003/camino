package com.ameec.camino.repositories;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ameec.camino.entities.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    Optional<Trip> findByUserId(Long userId);

    void deleteByUserId(Long userId);
    
}
