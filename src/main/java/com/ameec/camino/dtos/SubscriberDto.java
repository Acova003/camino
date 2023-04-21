package com.ameec.camino.dtos;

import com.ameec.camino.entities.Subscriber;
import com.ameec.camino.entities.Trip;
import com.ameec.camino.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberDto {
    private Long id;
    private Trip trip;
    private User subscribedUser;

    public SubscriberDto(SubscriberDto subscriber){
        if (subscriber.getId() != null) {
            this.id = subscriber.getId();
        }
        if (subscriber.getTrip() != null) {
            this.trip = subscriber.getTrip();
        }
        if (subscriber.getSubscribedUser() != null) {
            this.subscribedUser = subscriber.getSubscribedUser();
        }
    }
}
