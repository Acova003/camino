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
    // make a call to the user repository
    // find by id

    @Override
    public Optional<Trip> getTripByUserId(Long userId) {
        // pass the user id from the code above
        return tripRepository.findByUserId(userId);
    }
    
    @Override
    @Transactional
    public void deleteTripByUserId(Long userId) {
        tripRepository.deleteByUserId(userId);
    }

    // we got rid of subscriber dto 
    // what we actually need is to add and delete subscriptions as nessessary
    @Override
    @Transactional
    public void updateTripById(TripDto tripDto){
        // Optional<Trip> tripOptional = tripRepository.findById(tripDto.getId());
        // tripOptional.ifPresent(trip -> {
        //     trip.setSubscriberSet(tripDto.getSubscriptionDtoSet().stream()
        //             .map(Subscription::new)
        //             .collect(Collectors.toSet()));
        //     tripRepository.saveAndFlush(trip);
        // });
    }
    // service layers is where DTO's and entities meet
    @Override
    @Transactional
    public Double getKms_remaining(TripDto tripDto){
        return 0.0;
        // throwing an error because it's not returning anything
        // 1312.33595801 steps in a km
        // calculate kms to sanitiago
        // kms remaining = 1312.33595801 / total step count 

        // });
    }
}
