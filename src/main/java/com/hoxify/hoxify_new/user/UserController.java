package com.hoxify.hoxify_new.user;

import com.hoxify.hoxify_new.configration.CurrentUser;
import com.hoxify.hoxify_new.shared.GenericMessage;
import com.hoxify.hoxify_new.shared.Messages;
import com.hoxify.hoxify_new.user.dto.PasswordResetRequest;
import com.hoxify.hoxify_new.user.dto.PasswordUpdate;
import com.hoxify.hoxify_new.user.dto.UserCreate;
import com.hoxify.hoxify_new.user.dto.UserDTO;
import com.hoxify.hoxify_new.user.dto.UserUpdate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("api/v1/users")
    public GenericMessage createUser(@Valid @RequestBody UserCreate user) {
        userService.save(user.toUser());
        String message = Messages.getMessageForLocale("hoxify.create.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @PatchMapping("api/v1/users/{token}/active")
    public GenericMessage activateUser(@PathVariable String token) {
        userService.activateUser(token);
        String message = Messages.getMessageForLocale("hoxify.activate.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @GetMapping("api/v1/users")
    Page<UserDTO> getUsers(Pageable pageable,  @AuthenticationPrincipal CurrentUser currentUser) {
        return userService.getUsers(pageable, currentUser).map(UserDTO::new);
    }

    @GetMapping("api/v1/users/{id}")
    UserDTO getUser(@PathVariable Long id) {
        return new UserDTO(userService.getUser(id));
    }

    @PutMapping("api/v1/users/{id}")
    @PreAuthorize("#id == principal.id")
    UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdate user, @AuthenticationPrincipal CurrentUser currentUser) {
        return new UserDTO(userService.updateUser(id, user));
    }

    @PutMapping("api/v1/users/{id}")
    @PreAuthorize("#id == principal.id")
    GenericMessage deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new GenericMessage("User is deleted");
    }


    @PutMapping("api/v1/users/password-reset")
    GenericMessage passwordResetRequest(@Valid @RequestBody PasswordResetRequest passwordReset) {
        userService.handleResetRequest(passwordReset);
        return new GenericMessage("Check your Email Address to reset password");
    }
    @PatchMapping("/api/v1/users/{token}/password")
    GenericMessage setPassword(@PathVariable String token, @Valid @RequestBody PasswordUpdate passwordUpdate){
        userService.updatePassword(token, passwordUpdate);
        return new GenericMessage("Password updated successfully");

    }

}
