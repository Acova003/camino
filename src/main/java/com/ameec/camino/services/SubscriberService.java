package com.ameec.camino.services;

public interface SubscriberService {
    void subscribe(String subscriberEmail, String subscribedUserEmail);
    void endSubscription(String subscriberEmail, String subscribedUserEmail);
}
