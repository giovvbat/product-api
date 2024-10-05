package com.giovanna.demo.controller;

import com.giovanna.demo.dto.user.LoginRequestRecordDto;
import com.giovanna.demo.dto.user.LoginResponseRecordDto;
import com.giovanna.demo.dto.user.UpdateUserResponseDto;
import com.giovanna.demo.dto.user.UserRecordDto;
import com.giovanna.demo.model.UserModel;
import com.giovanna.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRecordDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseRecordDto> validateUser(@RequestBody @Valid LoginRequestRecordDto loginRequestRecordDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.validateUser(loginRequestRecordDto));
    }

    @PostMapping("/logout")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> logUserOut() {
        userService.logUserOut();
        return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully!");
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UpdateUserResponseDto> updateUser(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserRecordDto userRecordDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, userRecordDto));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully!");
    }
}
