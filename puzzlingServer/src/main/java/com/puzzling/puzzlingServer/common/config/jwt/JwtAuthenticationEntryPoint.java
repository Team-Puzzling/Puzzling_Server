package com.puzzling.puzzlingServer.common.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.puzzling.puzzlingServer.common.response.ApiResponse;
import com.puzzling.puzzlingServer.common.response.ErrorStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        setResponse(response, ErrorStatus.UNAUTHORIZED_TOKEN);
    }


    public void setResponse(HttpServletResponse response, ErrorStatus status) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiResponse apiResponse = ApiResponse.fail(response.getStatus(), status.getMessage());
        response.getWriter().println(mapper.writeValueAsString(apiResponse));
    }
}