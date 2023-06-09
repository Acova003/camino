package com.ameec.camino.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ameec.camino.entities.Subscription;
// import com.ameec.camino.entities.Trip;
import com.ameec.camino.entities.User;
import com.ameec.camino.repositories.SubscriberRepository;
import com.ameec.camino.repositories.TripRepository;
import com.ameec.camino.repositories.UserRepository;

@Service
public class SubscriberServiceImpl implements SubscriberService{
    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private SubscriberRepository SubscriberRepository;

    // @Autowired
    // private TripRepository TripRepository;
    
    @Transactional
    @Override
    public void subscribe(String subscribeeEmail, Long subscriberId) {
        Optional<User> subscribeeOptional = UserRepository.findByEmail(subscribeeEmail);
        Optional<User> subscriberOptional = UserRepository.findById(subscriberId);

        if (subscribeeOptional.isPresent() && subscriberOptional.isPresent()) {
            User subscribee = subscribeeOptional.get();
            User subscriber = subscriberOptional.get();
            // Optional<Trip> findTrip= TripRepository.findByUserId(subscribee.getId());

            // Check if the subscription already exists
            List<Subscription> subscriptions = SubscriberRepository.findAllBySubscriber(subscriber);
            for (Subscription subscription : subscriptions) {
                if (subscription.getSubscribee().equals(subscribee)) {
                    throw new RuntimeException("You are already subscribed to this user");
                }
        }
    
            // if (!findTrip.isPresent()) {
            //     // throw new RuntimeException("Trip not found.");
            // }
            Subscription subscription = new Subscription();
            subscription.setSubscriber(subscriber);
            subscription.setSubscribee(subscribee);
            // subscription.setTrip(findTrip.get());
            SubscriberRepository.save(subscription);
        } else {
            throw new RuntimeException("Subscriber or subscribed user not found.");
        }
    }

    @Transactional
    @Override
    public List<Subscription> getSubscriptions(Long subscriberId) {
        Optional<User> subscriberOptional = UserRepository.findById(subscriberId);
        if (subscriberOptional.isPresent()) {
            User subscriber = subscriberOptional.get();
            return SubscriberRepository.findAllBySubscriber(subscriber);
        } else {
            throw new RuntimeException("Subscriber not found.");
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

            Optional<Subscription> subscriptionOptional = SubscriberRepository.findBySubscriberAndSubscribee(subscriber, subscribedUser);

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
