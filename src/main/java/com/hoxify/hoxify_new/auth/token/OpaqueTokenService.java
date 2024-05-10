package com.hoxify.hoxify_new.auth.token;

import com.hoxify.hoxify_new.auth.dto.Credentials;
import com.hoxify.hoxify_new.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Optional;
import java.util.UUID;

@ConditionalOnProperty(name = "hoxify.token-type", havingValue = "basic")
public class OpaqueTokenService implements TokenService {
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public Token createToken(User user, Credentials credentials) {
        String randomValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setToken(randomValue);
        token.setUser(user);
        return tokenRepository.save(token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        var tokenInDB = getToken(authorizationHeader);
        if (!tokenInDB.isPresent()) return null;
        return tokenInDB.get().getUser();
    }

    @Override
    public void logout(String authorizationHeader) {
        var tokenInDB = getToken(authorizationHeader);
        if (!tokenInDB.isPresent()) return;
        tokenRepository.delete(tokenInDB.get());
    }

    private Optional<Token> getToken(String authorizationHeader) {
        if (authorizationHeader == null) return Optional.empty();
        var token = authorizationHeader.split(" ")[1];
        return tokenRepository.findById(token);
    }
}
