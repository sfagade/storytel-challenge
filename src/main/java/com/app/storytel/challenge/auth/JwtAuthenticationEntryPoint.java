package com.app.storytel.challenge.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author samsonfagade
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException {
        log.error("Responding with unauthorized error. Message - {}", e.getMessage());
        String message = e.getMessage();
        if (message != null && message.equals("Bad credentials")) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Sorry, You're not authorized to access this resource. Bad Credentials");

        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Sorry, You're not authorized to access this resource.");
        }
    }
}
