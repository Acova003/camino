package com.ameec.camino.repositories;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ameec.camino.entities.Subscription;
import com.ameec.camino.entities.User;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscription, Long>{

    Optional<Subscription> findBySubscriberAndSubscribedUser(User subscriber, User subscribedUser);
    
}
