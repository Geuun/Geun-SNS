package com.dev.geunsns.global.config.jwt.filter;

import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.service.UserService;
import com.dev.geunsns.global.config.jwt.utils.JwtUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	private final UserService userService;
	private final String secretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {

		final String token;
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		try {
			if (header == null || ! header.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			} else {
				token = header.split(" ")[1].trim();
				log.info("token : {}", token);
			}

			String userName = JwtUtils.getUsername(token, secretKey);
			UserEntity userDetails = userService.getUserByUserName(userName);
			log.info("userName: {}", userName);
			log.info("userDetailsName: {}", userDetails.getUserName());

			if (! JwtUtils.validate(token, userDetails.getUserName(), secretKey)) {
				filterChain.doFilter(request, response);
				return;
			}

			UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(userDetails,
														null,
														userDetails.getAuthorities());

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext()
								 .setAuthentication(authentication);
		} catch (RuntimeException e) {
			filterChain.doFilter(request, response);
			return;
		}
		filterChain.doFilter(request, response);
	}
}
