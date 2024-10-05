package com.giovanna.demo.controller;

import com.giovanna.demo.dto.store.StoreRecordDto;
import com.giovanna.demo.model.StoreModel;
import com.giovanna.demo.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<StoreModel> saveStore(@RequestBody @Valid StoreRecordDto storeRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.saveStore(storeRecordDto));
    }
}
