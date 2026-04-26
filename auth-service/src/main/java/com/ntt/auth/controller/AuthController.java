package com.ntt.auth.controller;

import com.ntt.auth.model.dto.LoginRequest;
import com.ntt.auth.model.dto.TokenResponse;
import com.ntt.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public Mono<ResponseEntity<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
    return authService.login(request).map(ResponseEntity::ok);
  }
}
