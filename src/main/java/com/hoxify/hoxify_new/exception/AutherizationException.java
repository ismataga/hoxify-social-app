package com.hoxify.hoxify_new.exception;

public class AutherizationException  extends RuntimeException{

    public AutherizationException() {
        super("Forbidden");
    }
}
