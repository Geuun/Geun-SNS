package com.dev.geunsns.global.config.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {
	private static Claims extractClaims(String token, String key) {
		return Jwts
			.parser()
			.setSigningKey(key)
			.parseClaimsJws(token)
			.getBody();
	}

	public static boolean isExpired(String token, String secretKey) {
		// expire timestamp를 반환
		Date expiredDate = extractClaims(token, secretKey).getExpiration();
		return expiredDate.before(new Date());
	}

	public static String getUserName(String token, String key) {
		return extractClaims(token, key)
			.get("userName")
			.toString();
	}


	// Token 유효성 확인
	public static Boolean validateToken(String token, String userName, String secretKey) {
		try {
			Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.SignatureException | MalformedJwtException e) {
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

	// Token 발급
	public static String generateAccessToken(String userName, String key, long expireTimeMs) {
		Claims claims = Jwts.claims(); // 일종의 map
		claims.put("userName", userName); // Claim : Token에 담는 정보

		return Jwts
			.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
			.signWith(SignatureAlgorithm.HS256, key)
			.compact()
			;
	}
}
