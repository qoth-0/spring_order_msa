package com.encore.ordering.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtGlobalFilter implements GlobalFilter {
    // api-gateway : webflux -> GlobalFilter 사용

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final List<String> allowUrl = Arrays.asList("/member/create", "/items", "/item/*/image", "/doLogin");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String reqUri = request.getURI().getPath();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean isAllowed = allowUrl.stream().anyMatch(uri -> antPathMatcher.match(uri, reqUri));
        if(isAllowed) { // allowUrl과 동일하면 토큰따질필요 x
            return chain.filter(exchange);
        }

        try {
            String bearerToken = request.getHeaders().getFirst("Authorization"); // header에 들어오는 bearerToken 가져오기
            if(bearerToken != null) { // null일 경우 패스
                //        Bearer 토큰인지 확인
                if (!bearerToken.substring(0, 7).equals("Bearer ")) {
                    throw new IllegalArgumentException("token의 형식이 맞지 않습니다.");
                }
                //        bearer 토큰에서 토큰 값만 추출
                String token = bearerToken.substring(7);
                //        토큰 검증 및 claims 추출
                Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
                String email = claims.getSubject();
                String role = claims.get("role", String.class); // role을 string으로 변환해서 받음
                request = exchange.getRequest().mutate()
                        .header("myEmail", email)
                        .header("myRole", role)
                        .build();
                exchange = exchange.mutate().request(request).build();
            }else {
                throw new RuntimeException("token is empty");
            }
        }catch(Exception e){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
}
