package com.ameec.camino.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ameec.camino.entities.Subscription;
import com.ameec.camino.entities.User;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscription, Long>{

    Optional<Subscription> findBySubscriberAndSubscribee(User subscriber, User subscribedUser);
    List<Subscription> findAllBySubscriber(User subscriber);

    
}
