package com.dev.geunsns.global.config.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";


    // DI
    private final JwtProvider jwtProvider;
    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 헤더에서 토큰을 받아온다. "Authorization: Bearer 토큰" 형식
        String token = resolveToken((HttpServletRequest) request);

        // 2. JwtProvider validateToken 메소드를 통해 토큰의 유효성을 검증한다.
        if (token != null && jwtProvider.validateToken(token)) {
            // 3. Redis 에 해당 accessToken 이 존재하는지 확인한다. (Logout 검증)
            String checkLogout = (String) redisTemplate.opsForValue().get(token);
            if (ObjectUtils.isEmpty(checkLogout)) {
//                // 토큰이 유효하면 토큰으로부터 회원 정보를 받아온다.
//                String userName = jwtProvider.getUserName(token);
                // 4. SecurityContext 에 Authentication 객체를 저장한다.
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    // Request Header 에서 Token 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
