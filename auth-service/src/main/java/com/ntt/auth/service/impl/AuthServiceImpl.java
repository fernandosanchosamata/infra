package com.ntt.auth.service.impl;

import com.ntt.auth.config.AuthProperties;
import com.ntt.auth.config.AuthProperties.MockUser;
import com.ntt.auth.config.JwtProperties;
import com.ntt.auth.model.dto.LoginRequest;
import com.ntt.auth.model.dto.TokenResponse;
import com.ntt.auth.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {

  private static final String TOKEN_TYPE = "Bearer";

  private final AuthProperties authProperties;
  private final JwtProperties jwtProperties;

  public AuthServiceImpl(AuthProperties authProperties, JwtProperties jwtProperties) {
    this.authProperties = authProperties;
    this.jwtProperties = jwtProperties;
  }

  @Override
  public Mono<TokenResponse> login(LoginRequest request) {
    return Mono.fromSupplier(() -> findUser(request))
        .map(this::buildTokenResponse);
  }

  private MockUser findUser(LoginRequest request) {
    return authProperties.getUsers().stream()
        .filter(user -> user.getUsername().equals(request.username()))
        .filter(user -> user.getPassword().equals(request.password()))
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas."));
  }

  private TokenResponse buildTokenResponse(MockUser user) {
    Instant issuedAt = Instant.now();
    Instant expiresAt = issuedAt.plus(jwtProperties.getExpirationMinutes(), ChronoUnit.MINUTES);

    String token =
        Jwts.builder()
            .setSubject(user.getSubject())
            .claim("roles", user.getRoles())
            .claim("customerId", user.getCustomerId())
            .setIssuedAt(Date.from(issuedAt))
            .setExpiration(Date.from(expiresAt))
            .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8))
            .compact();

    return new TokenResponse(TOKEN_TYPE, token, expiresAt, user.getSubject(), user.getCustomerId(), user.getRoles());
  }
}
