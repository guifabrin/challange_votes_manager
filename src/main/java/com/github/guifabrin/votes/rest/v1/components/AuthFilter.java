package com.github.guifabrin.votes.rest.v1.components;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.guifabrin.votes.rest.v1.entities.Associated;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();
        if (!"/".equals(path) && !path.startsWith("/api/v1/associated/login/") && !"/js/index.js".equals(path)) {
            Associated authAssociated = AuthManager.getByUUID(httpRequest.getHeader("uuid"));
            if (authAssociated == null)
                httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
        }
        chain.doFilter(request, httpResponse);
    }
}
