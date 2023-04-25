package com.ameec.camino.entities;

import com.ameec.camino.dtos.SubscriptionDto;

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
// change to subscription class
public class Subscription {
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

    public Subscription(SubscriptionDto subscriptionDto){
        if (subscriptionDto.getId() != null) {
            this.id = subscriptionDto.getId();
        }
        if (subscriptionDto.getTrip() != null) {
            this.trip = subscriptionDto.getTrip();
        }
        if (subscriptionDto.getSubscribedUser() != null) {
            this.subscriber = subscriptionDto.getSubscribedUser();
        }
    }

}
