package com.hoxify.hoxify_new.auth.token;

import com.hoxify.hoxify_new.auth.dto.Credentials;
import com.hoxify.hoxify_new.user.User;
import com.hoxify.hoxify_new.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@ConditionalOnProperty(name = "hoxify.token-type", havingValue = "basic")
public class BasicAuthTokenService implements TokenService {
    @Autowired
    UserService userService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Token createToken(User user, Credentials credentials) {
        String emailColonPassword = credentials.email() + ":" + credentials.password();

        String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());
        return new Token("Basic", token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        if (authorizationHeader == null) return null;
        var base64Encode = authorizationHeader.split("Basic")[1];
        var decoded = new String(Base64.getDecoder().decode(base64Encode));

        var credentials = decoded.split(":");
        var email = credentials[0];
        var password = credentials[1];

        User inDb = userService.findByEmail(email);
        if (inDb == null) return null;
        if (!passwordEncoder.matches(password, inDb.getPassword())) return null;
        return inDb;
    }

    @Override
    public void logout(String authorizationHeader) {

    }
}
