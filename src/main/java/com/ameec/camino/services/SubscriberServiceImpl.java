package com.ameec.camino.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ameec.camino.entities.Subscriber;
import com.ameec.camino.entities.User;
import com.ameec.camino.repositories.SubscriberRepository;
import com.ameec.camino.repositories.UserRepository;

public class SubscriberServiceImpl implements SubscriberService{
    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private SubscriberRepository SubscriberRepository;
    
    @Transactional
    @Override
    public void subscribe(String subscriberEmail, String subscribedUserEmail) {
        Optional<User> subscriberOptional = UserRepository.findByEmail(subscriberEmail);
        Optional<User> subscribedUserOptional = UserRepository.findByEmail(subscribedUserEmail);

        if (subscriberOptional.isPresent() && subscribedUserOptional.isPresent()) {
            User subscriber = subscriberOptional.get();
            User subscribedUser = subscribedUserOptional.get();

            Subscriber subscription = new Subscriber(null, null, subscriber, subscribedUser, subscribedUserEmail);
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

            Optional<Subscriber> subscriptionOptional = SubscriberRepository.findBySubscriberAndSubscribedUser(subscriber, subscribedUser);

            if (subscriptionOptional.isPresent()) {
                Subscriber subscription = subscriptionOptional.get();
                SubscriberRepository.delete(subscription);
            } else {
                throw new RuntimeException("Subscription not found.");
            }
        } else {
            throw new RuntimeException("Subscriber or subscribed user not found.");
        }
    }
}
