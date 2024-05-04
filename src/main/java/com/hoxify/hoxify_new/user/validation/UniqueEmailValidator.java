package com.hoxify.hoxify_new.user.validation;

import com.hoxify.hoxify_new.user.User;
import com.hoxify.hoxify_new.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    UserRepository userRepository;
    @Override
    public void initialize(UniqueEmail constraintAnnotation) {

    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println(value);
        if (value == null) {
            return true;
        }
        User inDB = userRepository.findByEmail(value);

        return inDB == null;
    }
}
