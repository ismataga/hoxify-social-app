package com.hoxify.hoxify_new.configration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "hoxify")
@Getter
@Setter
@Configuration
public class HoxifyProperties {
    private Email email;
    private Client client;

    public static record Email(
            String username,
            String password,
            String host,
            String from,
            Integer port
    ) {
    }

    public static record Client(
            String host
    ) {
    }
}
