package com.hoxify.hoxify_new;

import com.hoxify.hoxify_new.user.User;
import com.hoxify.hoxify_new.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HoxifyNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoxifyNewApplication.class, args);
    }


    @Bean
    @Profile("dev")
    CommandLineRunner userCreator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        var user1 = userRepository.findByEmail("user-1@gmail.com");
        if (user1 != null) return (CommandLineRunner) user1;
        return (args) -> {
            for (var i = 0; i <= 25; i++) {
                User user = new User();
                user.setEmail("user-" + i + "@gmail,com");
                user.setUsername("user-" + i);
                user.setPassword(passwordEncoder.encode("P4assword"));
                user.setActive(true);
                user.setFirstname("name " + i);
                user.setLastname("lastnae" + i);
                userRepository.save(user);
            }


        };
    }

}


