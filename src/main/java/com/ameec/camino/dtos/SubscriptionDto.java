package com.ameec.camino.dtos;

import com.ameec.camino.entities.Trip;
import com.ameec.camino.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private Long id;
    private Trip trip;
    private User subscribee;
    private User subscriber;

    public SubscriptionDto(SubscriptionDto subscription){
        if (subscription.getId() != null) {
            this.id = subscription.getId();
        }
        if (subscription.getTrip() != null) {
            this.trip = subscription.getTrip();
        }
        if (subscription.getSubscribee() != null) {
            this.subscribee = subscription.getSubscribee();
        }
        if (subscription.getSubscriber() != null) {
            this.subscriber = subscription.getSubscriber();
        }
    }
}
