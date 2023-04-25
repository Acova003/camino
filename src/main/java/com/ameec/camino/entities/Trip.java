package com.ameec.camino.entities;

import java.util.List;

import com.ameec.camino.dtos.TripDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "trip")
    private List<Subscription> subscriptions;

    @Column
    private String step_count;
    
    @Column
    private Double kms_remaining;

    public Trip(TripDto tripDto){
        if (tripDto.getKms_remaining() != null) {
            this.kms_remaining = tripDto.getKms_remaining();
        }
    }
}
