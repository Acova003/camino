package com.ameec.camino.entities;

import com.ameec.camino.dtos.SubscriberDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Subscribers")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private User subscriber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User subscribedUser;

    public Subscriber(SubscriberDto subscriberDto){
        if (subscriberDto.getId() != null) {
            this.id = subscriberDto.getId();
        }
        if (subscriberDto.getTrip() != null) {
            this.trip = subscriberDto.getTrip();
        }
        if (subscriberDto.getSubscribedUser() != null) {
            this.subscriber = subscriberDto.getSubscribedUser();
        }
    }
    
}
