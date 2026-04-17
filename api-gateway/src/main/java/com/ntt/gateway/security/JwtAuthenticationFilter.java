package com.ntt.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Evitar validación si la ruta es pública (como un MS de auth para sacar token)
        if (request.getURI().getPath().contains("/auth/")) {
            return chain.filter(exchange);
        }

        // Requiere header
        if (!request.getHeaders().containsKey("Authorization")) {
            return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String authHeader = request.getHeaders().getOrEmpty("Authorization").get(0);
        if (!authHeader.startsWith("Bearer ")) {
            return this.onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.replace("Bearer ", "");
        try {
             Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            // Si la firma del token es legítima, propagamos al backend el UserID de forma segura
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("X-UserId", claims.getSubject())
                    .build();
            
            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        } catch (Exception e) {
            return this.onError(exchange, "JWT Token Validation failed", HttpStatus.UNAUTHORIZED);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        // Retorna HTTP 401 puro e intercepta que siga a los ms de negocio
        return response.setComplete();
    }
}
