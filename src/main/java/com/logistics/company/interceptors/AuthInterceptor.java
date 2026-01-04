package com.logistics.company.interceptors;

import com.logistics.company.annotations.AuthGuard;
import com.logistics.company.models.enums.UserRole;
import com.logistics.company.services.AuthService;
import com.logistics.company.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        AuthGuard authGuard = handlerMethod.getMethodAnnotation(AuthGuard.class);
        if (authGuard == null) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("code", "UNAUTHORIZED");
            errorResponse.put("message", "No authorization token provided");
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            String json = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
            response.getWriter().flush();
            return false;
        }

        List<UserRole> requiredRoles = Arrays.stream(authGuard.value()).map(UserRole::valueOf).toList();
        boolean isAuthorized = jwtService.isUserAuthorized(token, requiredRoles);
        if (!isAuthorized) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("code", "UNAUTHORIZED");
            errorResponse.put("message", "Insufficient privileges");
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            String json = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
            response.getWriter().flush();
            return false;
        }

        return true;
    }
}
