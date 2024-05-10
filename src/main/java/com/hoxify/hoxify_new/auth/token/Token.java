package com.hoxify.hoxify_new.auth.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoxify.hoxify_new.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Token {
    @Id
    String token;

    @Transient
    String prefix = "Bearer";

    @ManyToOne
    @JsonIgnore
    User user;

}
