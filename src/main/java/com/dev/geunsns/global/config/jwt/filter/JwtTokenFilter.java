package com.dev.geunsns.global.config.jwt.filter;

import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.service.UserService;
import com.dev.geunsns.global.config.jwt.utils.JwtUtils;
import com.dev.geunsns.global.exception.GlobalErrorCode;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            try {
                filterChain.doFilter(request, response);
                return;
            } catch (Exception e) {
                request.setAttribute("exception", GlobalErrorCode.UNAUTHORIZED);
            }
        }

        try {
            String token = authorizationHeader.split(" ")[1];
            String userName = JwtUtils.getUsername(token, secretKey);
            UserEntity user = userService.getUserByUserName(userName);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getUserName(),
                    null,
                    List.of(new SimpleGrantedAuthority(user.getRole().name()))
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext()
                    .setAuthentication(authenticationToken);
        } catch (Exception e) {
            request.setAttribute("exception", GlobalErrorCode.UNAUTHORIZED.getErrorMessage());
        }
        filterChain.doFilter(request, response);
    }
}
