package com.hoxify.hoxify_new.exception;

import com.hoxify.hoxify_new.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class ActivationNotificationException extends  RuntimeException{
    public ActivationNotificationException(){
        super(Messages.getMessageForLocale("hoxify.create.user.email.failure", LocaleContextHolder.getLocale()));
    }
}
