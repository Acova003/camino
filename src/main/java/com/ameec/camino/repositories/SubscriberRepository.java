package com.ameec.camino.repositories;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ameec.camino.entities.Subscriber;
import com.ameec.camino.entities.User;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>{

    Optional<Subscriber> findBySubscriberAndSubscribedUser(User subscriber, User subscribedUser);
    
}
