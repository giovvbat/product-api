package com.giovanna.demo.controller;

import com.giovanna.demo.dto.product.ProductRecordDto;
import com.giovanna.demo.model.ProductModel;
import com.giovanna.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_MANAGER')")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productRecordDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE')")
    public ResponseEntity<ProductModel> getProductById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
    }

    @GetMapping("/name")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE')")
    public ResponseEntity<List<ProductModel>> getAllProductsByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductsByName(name));
    }

    @GetMapping("/stock/range")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE')")
    public ResponseEntity<List<ProductModel>> getAllProductsByStockRange(@RequestParam Integer minStock, @RequestParam Integer maxStock) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductsByStockRange(minStock, maxStock));
    }

    @GetMapping("/stock/less-than")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE')")
    public ResponseEntity<List<ProductModel>> getAllProductsByStockLessThan(@RequestParam Integer maxStock) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductsByStockLessThan(maxStock));
    }

    @GetMapping("/store")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE')")
    public ResponseEntity<List<ProductModel>> getAllProductsByStore(@RequestParam UUID storeId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductsByStore(storeId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE')")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }
}
