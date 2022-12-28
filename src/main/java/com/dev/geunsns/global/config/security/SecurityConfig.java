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

	public static final String [] permitList = {
			"/api/v1/**"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.httpBasic().disable()
			.csrf().disable()
			.cors().and()
			.authorizeRequests()
			.antMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll()
			.antMatchers("/api/v1/users/{id}/role/change").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST,"/api/v1/**").authenticated()
			.antMatchers(HttpMethod.PUT,"/api/v1/**").authenticated()
			.antMatchers(HttpMethod.DELETE,"/api/v1/**").authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
			.build();
	}
}
