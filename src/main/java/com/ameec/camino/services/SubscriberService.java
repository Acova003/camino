package com.ameec.camino.services;

import java.util.List;

import com.ameec.camino.entities.Subscription;

public interface SubscriberService {
    void subscribe(String subscribeeEmail, Long subscriberId);
    void endSubscription(String subscribeeEmail, String subscriberEmail);
    List<Subscription> getSubscriptions(Long subscriberId);
}
