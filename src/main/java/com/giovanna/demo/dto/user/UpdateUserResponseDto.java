package com.giovanna.demo.dto.user;

import com.giovanna.demo.model.UserModel;

public record UpdateUserResponseDto(UserModel user, String token) {
}
