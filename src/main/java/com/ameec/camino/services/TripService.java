package com.ameec.camino.services;

import com.ameec.camino.dtos.TripDto;
import com.ameec.camino.entities.Trip;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TripService {
    @Transactional
    Trip createTripAtSignup(Long userId);

    Optional<Trip> getTripByUserId(Long userId);

    @Transactional
    void deleteTripByUserId(Long userId);

    @Transactional
    void updateTripById(TripDto tripDto);

    @Transactional
    Double getKms_remaining(TripDto tripDto);
}
