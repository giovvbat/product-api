package com.giovanna.demo.service;

import com.giovanna.demo.dto.user.LoginRequestRecordDto;
import com.giovanna.demo.dto.user.LoginResponseRecordDto;
import com.giovanna.demo.dto.user.UpdateUserResponseDto;
import com.giovanna.demo.dto.user.UserRecordDto;
import com.giovanna.demo.infra.exception.role.RoleNotFoundException;
import com.giovanna.demo.infra.exception.user.*;
import com.giovanna.demo.infra.security.TokenService;
import com.giovanna.demo.model.RoleModel;
import com.giovanna.demo.model.UserModel;
import com.giovanna.demo.repository.RoleRepository;
import com.giovanna.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public UserModel saveUser(UserRecordDto userRecordDto) {
        RoleModel role = roleRepository.findById(userRecordDto.roleId())
                .orElseThrow(RoleNotFoundException::new);

        verifyAvailableUserCredentials(userRecordDto.username(), userRecordDto.email());

        UserModel user = new UserModel();
        user.setUsername(userRecordDto.username());
        user.setEmail(userRecordDto.email());
        user.setPassword(passwordEncoder.encode(userRecordDto.password()));
        user.setRole(role);

        return userRepository.save(user);
    }

    public LoginResponseRecordDto validateUser(LoginRequestRecordDto loginRequestRecordDto) {
        Optional<UserModel> user = userRepository.findByUsername(loginRequestRecordDto.username());

        if (user.isPresent()) {
            if (passwordEncoder.matches(loginRequestRecordDto.password(), user.get().getPassword())) {
                String token = tokenService.generateToken(user.get());
                return new LoginResponseRecordDto(user.get().getUsername(), token);
            }
        }

        throw new UnauthorizedCredentialsException();
    }

    public void logUserOut() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Transactional
    public UpdateUserResponseDto updateUser(UUID id, UserRecordDto userRecordDto) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        RoleModel role = roleRepository.findById(userRecordDto.roleId()).
                orElseThrow(RoleNotFoundException::new);

        verifyAvailableUserCredentials(userRecordDto.username(), userRecordDto.email());

        user.setUsername(userRecordDto.username());
        user.setEmail(userRecordDto.email());
        user.setPassword(passwordEncoder.encode(userRecordDto.password()));
        user.setRole(role);
        String token = tokenService.generateToken(user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && Objects.equals(authentication.getName(), userRecordDto.username())) {
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        }

        return new UpdateUserResponseDto(user, token);
    }

    @Transactional
    public void deleteUser(UUID id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(id);
    }

    private void verifyAvailableUserCredentials(String username, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyTakenException();
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyTakenException();
        }
    }
}
