package com.hoxify.hoxify_new.user.dto;

import org.springframework.beans.factory.annotation.Value;

public interface UserProjection {
    long getId();

    String getUsername();

    String getEmail();

    String getImage();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullname();
}
