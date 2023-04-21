package com.ameec.camino.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.ameec.camino.entities.Trip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDto implements Serializable{
    private Long id;
    private Double kms_remaining;
    private Long step_count;

    public TripDto(Trip trip) {
        if (trip.getId() != null){
            this.id = trip.getId();
        }
        // if (trip.getUser() != null){
        //     this.user = trip.getUser().getEmail();
        // }
        // if (trip.getSubscribers() != null) {
        //     this.subscriberDtoSet = trip.getSubscribers().stream()
        //             .map(SubscriberDto::new)
        //             .collect(Collectors.toSet());
        // }
        // finish
        // if (trip.getStep_count() != null) {
        if (trip.getKms_remaining() != null) {
            this.kms_remaining = trip.getKms_remaining();
        }
    }
}
