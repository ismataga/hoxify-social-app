package com.hoxify.hoxify_new.exception;

import com.hoxify.hoxify_new.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Collections;
import java.util.Map;

public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException() {
        super(Messages.getMessageForLocale("hoxify.error.validation", LocaleContextHolder.getLocale()));
    }

    public Map<String, String> getValidationErrors() {
        return Collections.singletonMap("email", Messages.getMessageForLocale("hoxify.constraint.email.notunique", LocaleContextHolder.getLocale()));
    }
}
