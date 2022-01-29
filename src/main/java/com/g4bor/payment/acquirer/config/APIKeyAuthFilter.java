package com.g4bor.payment.acquirer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class APIKeyAuthFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIKeyAuthFilter.class);
    private static final String requestHeaderKey = "Authentication-API-key";

    @Value("${http.apikey.acquirer}")
    private String acquirerApiKey;

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI();

        if(!path.startsWith("/api")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String clientApiKey = request.getHeader(requestHeaderKey);

        if (acquirerApiKey.equals(clientApiKey)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String error = "Invalid API Key";
            LOGGER.info(String.format("%s: %s",error, clientApiKey));
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentLength(error.length());
            response.getWriter().write(error);
        }
    }
}
