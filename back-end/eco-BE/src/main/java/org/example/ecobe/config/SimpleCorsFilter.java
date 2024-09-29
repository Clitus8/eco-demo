package org.example.ecobe.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.Filter;
import java.util.logging.LogRecord;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class SimpleCorsFilter implements Filter {

    @Value("${app.client.url}")
    private String clienturl="";


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        Map<String, String> map = new HashMap<>();
        String originHeader = req.getHeader("origin");

        log.info("CORS Filter triggered for request to: {}", req.getRequestURI());
        log.info("Origin Header: {}", originHeader);

        res.setHeader("Access-Control-Allow-Origin", originHeader);
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "*");
//        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

        if("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            log.info("OPTIONS request received, setting status to SC_OK");
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            log.info("Proceeding with the filter chain for request method: {}", req.getMethod());
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) {
        log.info("Initializing SimpleCorsFilter");
    }

    public void destroy() {
        log.info("Destroying SimpleCorsFilter");
    }
}
