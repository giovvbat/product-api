package com.giovanna.demo.dto.role;

import com.giovanna.demo.enums.UserAuthority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

public record RoleRecordDto(@NotEmpty Set<UserAuthority> authorities, @NotBlank String name) {
}
