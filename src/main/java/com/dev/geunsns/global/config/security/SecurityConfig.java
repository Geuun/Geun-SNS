package com.dev.geunsns.global.config.security;

import com.dev.geunsns.apps.user.service.UserService;
import com.dev.geunsns.global.config.jwt.filter.JwtTokenFilter;
import com.dev.geunsns.global.config.security.entrypoint.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	@Value("${jwt.token.secret}")
	private String secretKey;

	private final UserService userService;

	public static final String [] permitAllList = {
			"/api/v1/users/join",
			"/api/v1/users/login"
	};

	public static final String [] permitPostList = {
			"/api/v1/posts",
			"/api/v1/posts/*"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		// HTTP Basic Auth 인증창 해제
		httpSecurity.httpBasic().disable();
		// REST -> csrf 보안 필요 x -> Disavle
		httpSecurity.csrf().disable();

		// Cross-Origin Resource Sharing
		httpSecurity.cors();

		// path 권한 설정
		httpSecurity.authorizeRequests()
				.antMatchers(permitAllList).permitAll() // permitAll 세팅
				.antMatchers(HttpMethod.POST, permitPostList).authenticated(); //인증된 사용자에게만 허용

		// Jwt 사용을 위해서 Spring Security Session 정책 사용 x
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// 401 exception 처리
		httpSecurity.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

		//
		httpSecurity.addFilterBefore(new JwtTokenFilter(userService, secretKey),
				UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}
}
