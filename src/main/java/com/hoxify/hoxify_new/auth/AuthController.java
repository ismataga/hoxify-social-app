package com.hoxify.hoxify_new.auth;

import com.hoxify.hoxify_new.auth.dto.AuthResponse;
import com.hoxify.hoxify_new.auth.dto.Credentials;
import com.hoxify.hoxify_new.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/api/v1/auth")
    AuthResponse handleAuthentication(@Valid @RequestBody Credentials credentials) {
        return authService.authenticate(credentials);
    }

    @PostMapping("/api/v1/logout")
    GenericMessage handleLogout(@RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
        authService.logout(authorizationHeader);
        return new GenericMessage("Logged out success");
    }
}
