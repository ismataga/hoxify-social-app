package com.hoxify.hoxify_new.exception;

import com.hoxify.hoxify_new.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotFoundException  extends RuntimeException{
    public NotFoundException(long id) {

        super(Messages.getMessageForLocale("hoxify.user.not_found", LocaleContextHolder.getLocale(),id));
    }
}
