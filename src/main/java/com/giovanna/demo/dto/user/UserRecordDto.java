package com.giovanna.demo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRecordDto(@NotBlank String username, @NotBlank @Email String email, @NotBlank String password, @NotNull UUID roleId) {
}
