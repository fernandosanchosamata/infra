package com.ntt.auth.service;

import com.ntt.auth.model.dto.LoginRequest;
import com.ntt.auth.model.dto.TokenResponse;
import reactor.core.publisher.Mono;

public interface AuthService {

  Mono<TokenResponse> login(LoginRequest request);
}
