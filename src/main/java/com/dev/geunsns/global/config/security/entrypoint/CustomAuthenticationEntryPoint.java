package com.dev.geunsns.global.config.security.entrypoint;

import com.dev.geunsns.global.data.response.ErrorResponse;
import com.dev.geunsns.global.data.response.Response;
import com.dev.geunsns.global.exception.GlobalErrorCode;
import com.dev.geunsns.global.exception.GlobalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {

        log.info("ErrorMessage: {}", e.getMessage());

        ObjectMapper objectMapper = new ObjectMapper();

        GlobalErrorCode errorCode = GlobalErrorCode.UNAUTHORIZED;

        Response errorResponse = Response.error(new ErrorResponse(errorCode.getHttpStatus(), errorCode.getErrorMessage()));

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
