package io.careeros.coldemailer.dto.request;

public record CreateUserRequest(
    String email,
    String firstName,
    String lastName,
    String refreshToken
) {}
