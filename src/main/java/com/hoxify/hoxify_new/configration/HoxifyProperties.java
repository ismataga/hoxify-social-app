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
    private Storeage storeage = new Storeage();
    private String tokenType;

    public  record Email(
            String username,
            String password,
            String host,
            String from,
            Integer port
    ) {
    }

    public  record Client(
            String host
    ) {
    }


    @Setter
    @Getter
    public static  class Storeage{
        String root ="uploads";
        String profile = "profile";
    }
}
