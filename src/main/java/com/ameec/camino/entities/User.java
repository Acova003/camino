package com.ameec.camino.entities;

import java.util.Set;

import com.ameec.camino.dtos.UserDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column(unique = true)
    private String displayName; 

    @Column
    private Long steps;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subscription> subscriptions;

    @OneToMany(mappedBy = "subscribee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subscription> subscribers;

    public User(UserDto userDto){
        if (userDto.getId() != null){
            this.id = userDto.getId();
        }
        if (userDto.getEmail() != null){
            this.email = userDto.getEmail();
        }
        if (userDto.getPassword() != null){
            this.password = userDto.getPassword();
        }
        if (userDto.getDisplayName() != null){
            // change to camel case
            this.displayName = userDto.getDisplayName();
        }
        if (userDto.getSteps() != null){
            this.steps = userDto.getSteps();
        }
    }
}
