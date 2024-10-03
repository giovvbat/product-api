package com.giovanna.demo.controller;

import com.giovanna.demo.dto.stock.StockOperationRecordDto;
import com.giovanna.demo.model.StockOperationModel;
import com.giovanna.demo.service.StockOperationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<StockOperationModel> saveOperation(@RequestBody @Valid StockOperationRecordDto stockOperationRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockOperationService.saveOperation(stockOperationRecordDto));
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN, ROLE_MANAGER"})
    public ResponseEntity<StockOperationModel> getOperationById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(stockOperationService.getOperationById(id));
    }

    @GetMapping("/date/range")
    @Secured({"ROLE_ADMIN, ROLE_MANAGER"})
    public ResponseEntity<List<StockOperationModel>> getOperationByDateRange(@RequestParam LocalDateTime from, @RequestParam LocalDateTime to) {
        return ResponseEntity.status(HttpStatus.OK).body(stockOperationService.getOperationByDateRange(from, to));
    }

    @GetMapping("/store")
    @Secured({"ROLE_ADMIN, ROLE_MANAGER"})
    public ResponseEntity<List<StockOperationModel>> getOperationByStore(@RequestParam UUID storeId) {
        return ResponseEntity.status(HttpStatus.OK).body(stockOperationService.getOperationByStore(storeId));
    }

    @GetMapping
    @Secured({"ROLE_ADMIN, ROLE_MANAGER"})
    public ResponseEntity<List<StockOperationModel>> getAllOperations() {
        return ResponseEntity.status(HttpStatus.OK).body(stockOperationService.getAllOperations());
    }
}
