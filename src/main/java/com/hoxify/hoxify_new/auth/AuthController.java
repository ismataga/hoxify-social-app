package com.hoxify.hoxify_new.auth;

import com.hoxify.hoxify_new.auth.dto.AuthResponse;
import com.hoxify.hoxify_new.auth.dto.Credentials;
import com.hoxify.hoxify_new.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/api/v1/auth")
    ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody Credentials credentials) {
        var authResponse = authService.authenticate(credentials);
        var cookie = ResponseCookie.from("hoax-token", authResponse.getToken().getToken()).path("/").httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
    }

    @PostMapping("/api/v1/logout")
    GenericMessage handleLogout(@RequestHeader(name = "Authorization", required = false) String authorizationHeader, @CookieValue(name = "hoax-token",
            required = false) String coocieValue) {
        var tokenWithPrefix = authorizationHeader;
        if (coocieValue == null) {
            tokenWithPrefix = "AnyPrefix " + coocieValue;
        }
        authService.logout(tokenWithPrefix);
        return new GenericMessage("Logged out success");
    }
}
