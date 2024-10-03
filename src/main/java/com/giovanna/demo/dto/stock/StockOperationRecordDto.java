package com.giovanna.demo.dto.stock;

import com.giovanna.demo.enums.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record StockOperationRecordDto(@NotNull UUID productId, @NotNull OperationType operationType, @NotNull @Positive Integer productQuantity) {
}
