package com.hoxify.hoxify_new.auth.dto;

import com.hoxify.hoxify_new.auth.token.Token;
import com.hoxify.hoxify_new.user.dto.UserDTO;
import lombok.Data;

@Data
public class AuthResponse {
    UserDTO userDTO;
    Token token;
}
