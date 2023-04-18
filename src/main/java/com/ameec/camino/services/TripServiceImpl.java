package com.ameec.camino.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ameec.camino.dtos.TripDto;
import com.ameec.camino.entities.Trip;
import com.ameec.camino.entities.User;
import com.ameec.camino.repositories.TripRepository;
import com.ameec.camino.repositories.UserRepository;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TripRepository tripRepository;

    @Override
    @Transactional
    public Trip createTripAtSignup(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Trip trip = new Trip();
            trip.setUser(user);

            return tripRepository.save(trip);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    @Override
    public Optional<Trip> getTripByUserId(Long userId) {
        return tripRepository.findByUserId(userId);
    }
    
    @Override
    @Transactional
    public void deleteTripByUserId(Long userId) {
        tripRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void updateTripById(TripDto tripDto){
        Optional<Trip> tripOptional = tripRepository.findById(tripDto.getId());
        tripOtional.ifPresent(trip -> {
            trip.setSubscriberSet(tripDto.getSubscriberDtoSet().stream()
                    .map(Subscriber::new)
                    .collect(Collectors.toSet()));
            tripRepository.saveAndFlush(trip);
        });
    }
}
