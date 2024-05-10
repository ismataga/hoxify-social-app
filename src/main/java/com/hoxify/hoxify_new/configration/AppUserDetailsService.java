package com.hoxify.hoxify_new.configration;

import com.hoxify.hoxify_new.user.User;
import com.hoxify.hoxify_new.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User inDb = userService.findByEmail(username);
        if (inDb == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CurrentUser(inDb);
    }
}
