package com.ameec.camino.entities;

import com.ameec.camino.dtos.UserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String display_name; 

    @Column
    private String step_count;

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
        if (userDto.getDisplay_name() != null){
            this.display_name = userDto.getDisplay_name();
        }
    }
}
