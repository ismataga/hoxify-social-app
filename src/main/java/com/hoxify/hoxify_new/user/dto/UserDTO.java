package com.hoxify.hoxify_new.user.dto;

import com.hoxify.hoxify_new.user.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String image;
    private String fullname;

    public UserDTO(User user){
        setId(user.getId());
        setUsername(user.getUsername());
        setEmail(user.getEmail());
        setFullname(user.getFirstname()+" "+user.getLastname());
        setImage(user.getImage());
    }
}
