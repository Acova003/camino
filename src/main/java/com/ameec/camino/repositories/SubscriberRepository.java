package com.ameec.camino.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ameec.camino.entities.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>{
    
}
