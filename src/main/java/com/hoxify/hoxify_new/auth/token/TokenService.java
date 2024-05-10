package com.hoxify.hoxify_new.auth.token;


import com.hoxify.hoxify_new.auth.dto.Credentials;
import com.hoxify.hoxify_new.user.User;
import org.springframework.stereotype.Service;

public interface TokenService {
     Token createToken(User user, Credentials credentials);
     User verifyToken(String authorizationHeader);

     public void logout(String authorizationHeader);
}
