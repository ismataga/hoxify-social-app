package com.hoxify.hoxify_new.auth;

import com.hoxify.hoxify_new.auth.dto.AuthResponse;
import com.hoxify.hoxify_new.auth.dto.Credentials;
import com.hoxify.hoxify_new.auth.exception.AuthenticationException;
import com.hoxify.hoxify_new.auth.token.Token;
import com.hoxify.hoxify_new.auth.token.TokenService;
import com.hoxify.hoxify_new.user.User;
import com.hoxify.hoxify_new.user.UserService;
import com.hoxify.hoxify_new.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    public AuthResponse authenticate(Credentials credentials) {
        User inDb = userService.findByEmail(credentials.email());
        if (inDb == null) throw new AuthenticationException();
        if (!passwordEncoder.matches(credentials.password(), inDb.getPassword())) throw new AuthenticationException();

        Token token = tokenService.createToken(inDb, credentials);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUserDTO(new UserDTO(inDb));
        return authResponse;
    }

    public void logout(String authorizationHeader) {
        tokenService.logout(authorizationHeader);

    }
}
