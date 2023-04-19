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
    private String display_name;
    private User subscribedUser;

    public SubscriberDto(Subscriber subscriber){
        if (subscriber.getId() != null) {
            this.id = subscriber.getId();
        }
        if (subscriber.getTrip() != null) {
            this.trip = subscriber.getTrip();
        }
        if (subscriber.getDisplay_name() != null) {
            this.display_name = subscriber.getDisplay_name();
        }
        if (subscriber.getSubscribedUser() != null) {
            this.subscribedUser = subscriber.getSubscribedUser();
        }
    }
}
