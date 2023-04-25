package com.ameec.camino.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ameec.camino.entities.Subscription;
import com.ameec.camino.entities.Trip;
import com.ameec.camino.entities.User;
import com.ameec.camino.repositories.SubscriberRepository;
import com.ameec.camino.repositories.TripRepository;
import com.ameec.camino.repositories.UserRepository;

public class SubscriberServiceImpl implements SubscriberService{
    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private SubscriberRepository SubscriberRepository;

    @Autowired
    private TripRepository TripRepository;
    
    @Transactional
    @Override
    public void subscribe(String subscriberEmail, String subscribedUserEmail) {
        Optional<User> subscriberOptional = UserRepository.findByEmail(subscriberEmail);
        // Optional<User> subscriberOptional = SubscriberRepository.findByEmail(subscriberEmail);
        Optional<User> subscribedUserOptional = UserRepository.findByEmail(subscribedUserEmail);

        if (subscriberOptional.isPresent() && subscribedUserOptional.isPresent()) {
            User subscriber = subscriberOptional.get();
           
            // change name to subscription
            User subscribedUser = subscribedUserOptional.get();
            Optional<Trip> findTrip= TripRepository.findByUserId(subscribedUser.getId());
            // check if findTrip is present
            // if not, throw an error
            // include code below in the if statement
            Subscription subscription = new Subscription();
            subscription.setSubscriber(subscriber);
            // whatever I'm going to pull from subscribed user is going to go in the argument below
            subscription.setTrip(findTrip.get());
            SubscriberRepository.save(subscription);
        } else {
            throw new RuntimeException("Subscriber or subscribed user not found.");
        }
    }
    @Transactional
    @Override
    public void endSubscription(String subscriberEmail, String subscribedUserEmail) {
        Optional<User> subscriberOptional = UserRepository.findByEmail(subscriberEmail);
        Optional<User> subscribedUserOptional = UserRepository.findByEmail(subscribedUserEmail);

        if (subscriberOptional.isPresent() && subscribedUserOptional.isPresent()) {
            User subscriber = subscriberOptional.get();
            User subscribedUser = subscribedUserOptional.get();

            Optional<Subscription> subscriptionOptional = SubscriberRepository.findBySubscriberAndSubscribedUser(subscriber, subscribedUser);

            if (subscriptionOptional.isPresent()) {
                Subscription subscription = subscriptionOptional.get();
                SubscriberRepository.delete(subscription);
            } else {
                throw new RuntimeException("Subscription not found.");
            }
        } else {
            throw new RuntimeException("Subscriber or subscribed user not found.");
        }
    }
}
