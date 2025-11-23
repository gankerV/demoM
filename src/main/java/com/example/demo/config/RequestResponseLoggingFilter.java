package com.example.demo.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        long start = System.currentTimeMillis();

        log.info("Incoming request: method={}, path={}, remoteAddr={}, contentType={}",
                req.getMethod(),
                req.getRequestURI(),
                req.getRemoteAddr(),
                req.getContentType()
        );

        chain.doFilter(request, response);

        HttpServletResponse resp = (HttpServletResponse) response;

        log.info("Completed request: method={}, path={}, status={}, durationMs={}",
                req.getMethod(),
                req.getRequestURI(),
                resp.getStatus(),
                (System.currentTimeMillis() - start)
        );
    }
}
