package com.hoxify.hoxify_new.user;

import com.hoxify.hoxify_new.error.ApiError;
import com.hoxify.hoxify_new.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("api/v1/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        ApiError apiError =  ApiError
                .builder()
                .path("/api/v1/users")
                .message("Validation error")
                .status(400)
                .build();
        Map<String, String> validationErrorMap = new HashMap<String, String>();
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            validationErrorMap.put("username", "Username is required");
        }

        if(validationErrorMap.size() > 0) {
            apiError.setValidationErrors(validationErrorMap);
            return ResponseEntity.badRequest().body(apiError);
        }

        userService.save(user);
        return ResponseEntity.ok(new GenericMessage("User is created"));
    }
}
