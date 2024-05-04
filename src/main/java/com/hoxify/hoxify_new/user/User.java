package com.hoxify.hoxify_new.user;

import com.hoxify.hoxify_new.user.validation.UniqueEmail;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{hoxify.constraints.username.notBlank}")
//    @Size(min = 4, max =255)
    private String username;

    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @Size(min =8, max =255)
    @Pattern(regexp ="(^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$)", message= "{hoxify.constraint.password.notBlank}")
    private String password;
}
