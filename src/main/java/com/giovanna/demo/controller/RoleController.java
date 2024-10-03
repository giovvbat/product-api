package com.giovanna.demo.controller;

import com.giovanna.demo.dto.role.RoleRecordDto;
import com.giovanna.demo.model.RoleModel;
import com.giovanna.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<RoleModel> saveRole(@RequestBody RoleRecordDto roleRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveRole(roleRecordDto));
    }

    @PostMapping("/first-admin")
    public ResponseEntity<RoleModel> saveFirstAdminRole() {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveFirstAdminRole());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<RoleModel> getRoleById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<RoleModel>> getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllRoles());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<RoleModel> updateRole(@PathVariable UUID id, RoleRecordDto roleRecordDto) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(id, roleRecordDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).body("Role deleted successfully!");
    }
}
