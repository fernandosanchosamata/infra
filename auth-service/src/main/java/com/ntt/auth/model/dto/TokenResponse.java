package com.ntt.auth.model.dto;

import java.time.Instant;
import java.util.List;

public record TokenResponse(
    String tokenType,
    String accessToken,
    Instant expiresAt,
    String subject,
    String customerId,
    List<String> roles) {}
