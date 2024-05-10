package com.hoxify.hoxify_new.error;

import com.hoxify.hoxify_new.auth.exception.AuthenticationException;
import com.hoxify.hoxify_new.exception.ActivationNotificationException;
import com.hoxify.hoxify_new.exception.AutherizationException;
import com.hoxify.hoxify_new.exception.InvalidTokenException;
import com.hoxify.hoxify_new.exception.NotFoundException;
import com.hoxify.hoxify_new.exception.NotUniqueEmailException;
import com.hoxify.hoxify_new.shared.Messages;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandlers {
    @ExceptionHandler({MethodArgumentNotValidException.class,
            NotUniqueEmailException.class,
            InvalidTokenException.class,
            NotFoundException.class,
            AuthenticationException.class,
            ActivationNotificationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleException(Exception exception, HttpServletRequest request) {

        ApiError apiError = ApiError
                .builder()
                .path(request.getRequestURI())
                .message(exception.getMessage())
                .build();

        switch (exception) {
            case MethodArgumentNotValidException methodArgumentNotValidException -> {
                String message = Messages.getMessageForLocale("hoxify.error.validation", LocaleContextHolder.getLocale());

                Map<String, String> validationErrorMap = new HashMap<>();
                methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(fieldError -> {
                    validationErrorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                });

                ApiError.builder()
                        .message(message)
                        .status(400)
                        .validationErrors(validationErrorMap)
                        .build();
            }
            case NotUniqueEmailException notUniqueEmailException -> ApiError.builder()
                    .status(400)
                    .validationErrors(notUniqueEmailException.getValidationErrors())
                    .build();
            case ActivationNotificationException activationNotificationException -> ApiError.builder()
                    .status(502)
                    .build();
            case InvalidTokenException invalidTokenException -> ApiError.builder()
                    .status(400)
                    .build();
            case NotFoundException notFoundException -> ApiError.builder()
                    .status(404)
                    .build();
            case AuthenticationException authenticationException -> ApiError.builder()
                    .status(401)
                    .build();
            case AutherizationException autherizationException -> ApiError.builder()
                    .status(403)
                    .build();
            default -> {
            }
        }
        return apiError;
    }

}
