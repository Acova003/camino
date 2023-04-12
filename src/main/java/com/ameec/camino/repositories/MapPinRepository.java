package com.ameec.camino.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ameec.camino.entities.MapPin;

@Repository
public interface MapPinRepository extends JpaRepository<MapPin, Long>{
    
}
