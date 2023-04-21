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
    private String display_name;
    private String step_count;

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
        if (user.getDisplay_name() != null){
            this.display_name = user.getDisplay_name();
        }
        // add if block for step count
        // for both dto and entity
    }
}
