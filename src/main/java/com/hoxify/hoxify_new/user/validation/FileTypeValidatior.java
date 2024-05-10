package com.hoxify.hoxify_new.user.validation;

import com.hoxify.hoxify_new.file.FileService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FileTypeValidatior implements ConstraintValidator<FIleType, String> {

    @Autowired
    FileService fileService;

    String[] types;

    @Override
    public void initialize(FIleType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        String type = fileService.detectType(value);
        for (String validTypes : types) {
            if (type.contains(validTypes)) return true;
        }

        String validTypes = Arrays.stream(types).collect(Collectors.joining(", "));
        context.disableDefaultConstraintViolation();
        HibernateConstraintValidatorContext unwrap = context.unwrap(HibernateConstraintValidatorContext.class);
        unwrap.addMessageParameter("types", validTypes);
        unwrap.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
        return false;
    }
}
