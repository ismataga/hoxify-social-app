package com.hoxify.hoxify_new.user.dto;

import com.hoxify.hoxify_new.user.User;
import com.hoxify.hoxify_new.user.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserCreate(

        @NotBlank(message = "{hoxify.constraints.username.notBlank}")
        @Size(min = 4, max = 255)
        String username,

        @NotBlank
        @Email
        @UniqueEmail
        String email,

        @Size(min = 8, max = 255)
        @Pattern(regexp = "(^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$)", message = "{hoxify.constraint.password.notBlank}")
        String password
) {
    public User toUser() {
        return User
                .builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
