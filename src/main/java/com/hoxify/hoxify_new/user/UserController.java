package com.hoxify.hoxify_new.user;

import com.hoxify.hoxify_new.error.ApiError;
import com.hoxify.hoxify_new.exception.NotUniqueEmailException;
import com.hoxify.hoxify_new.shared.GenericMessage;
import com.hoxify.hoxify_new.shared.Messages;
import com.hoxify.hoxify_new.user.dto.UserCreate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;
//    @Autowired
//    MessageSource messageSource;

    @PostMapping("api/v1/users")
    public GenericMessage createUser(@Valid @RequestBody UserCreate user) {
        userService.save(user.toUser());
        String message = Messages.getMessageForLocale("hoxify.create.user.success.message",LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleMethodArgNotValidEx(MethodArgumentNotValidException exception) {

        String message = Messages.getMessageForLocale("hoxify.error.validation", LocaleContextHolder.getLocale());

        ApiError apiError = ApiError
                .builder()
                .path("/api/v1/users")
                .message(message)
                .status(400)
                .build();
        Map<String, String> validationErrorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            validationErrorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        apiError.setValidationErrors(validationErrorMap);
        return apiError;
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleNotUniqueEmailEx(NotUniqueEmailException exception) {
        ApiError apiError = ApiError
                .builder()
                .path("/api/v1/users")
                .message(exception.getMessage())
                .status(400)
                .build();
        apiError.setValidationErrors(exception.getValidationErrors());
        return apiError;
    }
}
