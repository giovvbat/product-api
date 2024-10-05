package com.giovanna.demo.controller;

import com.giovanna.demo.dto.stock.StockOperationRecordDto;
import com.giovanna.demo.model.StockOperationModel;
import com.giovanna.demo.service.StockOperationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stock-operations")
public class StockOperationController {
    @Autowired
    private StockOperationService stockOperationService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<StockOperationModel> saveOperation(@RequestBody @Valid StockOperationRecordDto stockOperationRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockOperationService.saveOperation(stockOperationRecordDto));
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<StockOperationModel> getOperationById(@PathVariable(value = "id") @NotNull @Validated UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(stockOperationService.getOperationById(id));
    }

    @GetMapping("/date/range")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<List<StockOperationModel>> getOperationByDateRange(@RequestParam @NotNull @Validated LocalDateTime from, @RequestParam @NotNull @Validated LocalDateTime to) {
        return ResponseEntity.status(HttpStatus.OK).body(stockOperationService.getOperationByDateRange(from, to));
    }

    @GetMapping("/store/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<List<StockOperationModel>> getOperationByStore(@PathVariable(value = "id") @NotNull @Validated UUID storeId) {
        return ResponseEntity.status(HttpStatus.OK).body(stockOperationService.getOperationByStore(storeId));
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<List<StockOperationModel>> getAllOperations() {
        return ResponseEntity.status(HttpStatus.OK).body(stockOperationService.getAllOperations());
    }
}
