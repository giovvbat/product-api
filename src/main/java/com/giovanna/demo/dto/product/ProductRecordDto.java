package com.giovanna.demo.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRecordDto(@NotBlank String name, @NotBlank String description, @NotNull @Positive BigDecimal productionPrice, @NotNull @Positive BigDecimal sellPrice, @NotNull @Positive Integer stock, @NotNull UUID storeId) {
}
