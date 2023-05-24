package com.ameec.camino.dtos;

import java.io.Serializable;

import com.ameec.camino.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String email;
    private String password;
    private String displayName;
    private Long steps;

    public UserDto(User user) {
        if (user.getId() != null){
            this.id = user.getId();
        }
        if (user.getEmail() != null){
            this.email = user.getEmail();
        }
        if (user.getPassword() != null){
            this.password = user.getPassword();
        }
        if (user.getDisplayName() != null){
            this.displayName = user.getDisplayName();
        }
        if (user.getSteps() != null){
            this.steps = user.getSteps();
        }
    }
}
