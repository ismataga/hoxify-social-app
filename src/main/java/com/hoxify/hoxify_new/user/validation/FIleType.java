package com.hoxify.hoxify_new.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileTypeValidatior.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FIleType {
    String message() default "Only {types} are supported";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String [] types();
}
