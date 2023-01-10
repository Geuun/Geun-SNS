package com.dev.geunsns.global.config.jwt;

import com.dev.geunsns.apps.user.data.dto.response.UserLoginResponse;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    /**
     * JWT 토큰을 생성하고 검증하는 클래스
     * JWT 토큰은 Header, Payload, Signature로 구성되어 있음
     * Header : 토큰의 타입과 해싱 알고리즘 정보
     * Payload : 토큰에 담을 정보
     * Signature : 토큰의 유효성 검증을 위한 서명
     */

    private static final String AUTHORITIES_KEY = "auth";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;              // Access Token 의 유효기간 : 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;    // Refresh Token 의 유효기간 : 7일

    private final Key key;

    // Secret Key 를 Base64 Decode
    public JwtProvider(@Value("${jwt.token.secret}")String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 유저정보를 통해 Token 생성하는 메서드
    public String generateToken(UserEntity userEntity) {

        long now = (new Date().getTime());
        
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(userEntity.getUserName())
                .claim(AUTHORITIES_KEY, userEntity.getRole())
                .signWith(SignatureAlgorithm.HS256, key)
                .setIssuedAt(new Date(now))
                .setExpiration(accessTokenExpiresIn)
                .compact();

        return accessToken;
    }

    public String generateRefreshToken(UserEntity userEntity) {
        // Refresh Token 생성

        long now = (new Date().getTime());

        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return refreshToken;
    }
    
    // JWT Token을 복호화한 뒤 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // Token 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // Claim에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 생성하여 Authentication 객체를 리턴
        // 여기서 사용하는 User 객체는 SpringBoot Security 기본 객체
        UserDetails principal = new User(claims.getId(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    // Token 정보 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // accessToken의 남은 유효시간을 가져오는 메서드
    public Long getExpiration(String accessToken) {
        // 남은 유효시간
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();

        // 현재 시간
        Long now = new Date().getTime();

        // 남은 시간
        Long remainTime = expiration.getTime() - now;

        return remainTime;
    }

    public String getUserName(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

