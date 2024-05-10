package com.hoxify.hoxify_new.user.dto;

import com.hoxify.hoxify_new.user.validation.FIleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdate (
        @NotBlank(message = "hoxify.constraints.username.notBlank}")
        @Size(min = 4, max = 255)
        String username,
        @FIleType(types = {"jpeg", "png"})
        String image
){
}
