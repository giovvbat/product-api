package com.giovanna.demo.controller;

import com.giovanna.demo.dto.product.ProductRecordDto;
import com.giovanna.demo.model.ProductModel;
import com.giovanna.demo.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productRecordDto));
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<ProductModel> getProductById(@PathVariable(value = "id") @NotNull @Validated UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
    }

    @GetMapping("/name")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<ProductModel>> getAllProductsByName(@RequestParam(value = "name") @NotBlank @Validated String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductsByName(name));
    }

    @GetMapping("/stock/range")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<ProductModel>> getAllProductsByStockRange(@RequestParam(value = "min-stock") @NotNull @PositiveOrZero @Validated Integer minStock, @RequestParam(value = "max-stock") @NotNull @Positive @Validated Integer maxStock) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductsByStockRange(minStock, maxStock));
    }

    @GetMapping("/stock/less-than")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<ProductModel>> getAllProductsByStockLessThan(@RequestParam @NotNull @PositiveOrZero @Validated Integer maxStock) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductsByStockLessThan(maxStock));
    }

    @GetMapping("/store/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<ProductModel>> getAllProductsByStore(@PathVariable(value = "id") @NotNull @Validated UUID storeId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductsByStore(storeId));
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }
}
