package com.giovanna.demo.dto.store;

import jakarta.validation.constraints.NotBlank;

public record StoreRecordDto(@NotBlank String name, @NotBlank String address) {
}
