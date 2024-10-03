package com.giovanna.demo.dto.user;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestRecordDto(@NotBlank String username, @NotBlank String password) {
}
