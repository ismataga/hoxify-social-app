package com.hoxify.hoxify_new.exception;

import com.hoxify.hoxify_new.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException() {
        super(Messages.getMessageForLocale("hoxify_new.invalid_token", LocaleContextHolder.getLocale()));
    }
}
