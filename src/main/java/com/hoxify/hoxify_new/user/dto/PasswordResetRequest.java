package com.hoxify.hoxify_new.user.dto;

import jakarta.validation.constraints.Email;

public record PasswordResetRequest(@Email String email) {
}
