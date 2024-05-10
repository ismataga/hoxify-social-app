package com.hoxify.hoxify_new.auth.exception;

import com.hoxify.hoxify_new.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException() {
        super(Messages.getMessageForLocale("hoxify_new.auth.invalid.credentials", LocaleContextHolder.getLocale()));
    }
}
